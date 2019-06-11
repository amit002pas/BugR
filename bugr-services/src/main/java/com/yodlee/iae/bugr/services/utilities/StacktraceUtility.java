package com.yodlee.iae.bugr.services.utilities;

import java.util.LinkedHashSet;

/**
 * @author adubey & Sanyam Jain
 *
 */
public final class StacktraceUtility {

	public static final int DESCRIPTON_LENGTH = 20;

	public static String formatComment(String stackTraceFromComment) {
		if (stackTraceFromComment.contains("stacktrace:")) {
			stackTraceFromComment = stackTraceFromComment.replace("stacktrace:", "stack trace:");
		} else if (stackTraceFromComment.contains("ExceptionStacktrace:")) {
			stackTraceFromComment = stackTraceFromComment.replace("ExceptionStacktrace:", "stack trace:");
		} else if (stackTraceFromComment.contains("Stack Trace:")) {
			stackTraceFromComment = stackTraceFromComment.replace("Stack Trace:", "stack trace:");
		} else if (stackTraceFromComment.contains("Stack trace:")) {
			stackTraceFromComment = stackTraceFromComment.replace("Stack trace:", "stack trace:");
		}
		return stackTraceFromComment;
	}

	public static String findExceptionName(String stackTrace) {

		String execptionName = "";
		if (stackTrace.contains("Error communicating with the remote browser")) {
			return "UnreachableBrowserException";
		} else {
			try {
				if (stackTrace.contains(".java") && stackTrace.indexOf("stack trace:") != -1) {
					execptionName = stackTrace.substring(stackTrace.indexOf("stack trace:") + 12,
							stackTrace.indexOf(".java:"));
					// execptionName = stackTrace.substring(0, stackTrace.indexOf("at "));
					if (execptionName.contains(":")) {
						execptionName = execptionName.substring(0, execptionName.indexOf(":")).trim();
						execptionName = execptionName.substring(execptionName.lastIndexOf(".") + 1).trim();
					} else {
						execptionName = execptionName.substring(execptionName.lastIndexOf(".") + 1).trim();
					}
				} else {
					if(stackTrace.contains("WebDriverException")) {
						execptionName = stackTrace.substring(stackTrace.indexOf("WebDriverException")).trim();
						execptionName = execptionName.substring(0, execptionName.indexOf(":")).trim();
					} else {
						if(stackTrace.contains("Exception:")) {
							execptionName = stackTrace.substring(stackTrace.indexOf("stack trace")+12).trim();
							execptionName = execptionName.substring(0, execptionName.indexOf("Exception:")+9);
							execptionName = execptionName.substring(execptionName.lastIndexOf(".")+1, execptionName.length()).trim();
						}
					}
				}

			} catch (Exception e) {
				if (stackTrace.indexOf(".java:") > stackTrace.indexOf("exception:") && stackTrace.indexOf("exception:") != -1) {

					execptionName = stackTrace.substring(stackTrace.indexOf("exception:") + 12,
							stackTrace.indexOf(".java:"));
					// execptionName = stackTrace.substring(0, stackTrace.indexOf("at "));

					if (execptionName.contains(":")) {
						execptionName = execptionName.substring(0, execptionName.indexOf(":")).trim();
						execptionName = execptionName.substring(execptionName.lastIndexOf(".") + 1).trim();
					} else {
						execptionName = execptionName.substring(execptionName.lastIndexOf(".") + 1).trim();
					}

				} 
			}
			return execptionName;
		}

	}

	public static String findExceptionDescription(String stackTrace, String execptionName) {

		String exceptionName1 = "";
		String execptionDesc = "";
		//stackTrace = stackTrace.toLowerCase();
		if (stackTrace.contains("Error communicating with the remote browser")) {
			return "Error communicating with the remote browser";
		} else {
			try {
				if (stackTrace.contains(".java") && stackTrace.indexOf("stack trace:") != -1) {
					execptionDesc = stackTrace.substring(stackTrace.indexOf("stack trace:") + 12,
							stackTrace.indexOf(".java:"));

					if(execptionDesc.contains("at "))
						execptionDesc = execptionDesc.substring(0, execptionDesc.indexOf("at ")).trim();

					if (execptionDesc.contains(":")) {
						exceptionName1 = execptionDesc.substring(0, execptionDesc.indexOf(":")+1).trim();

						execptionDesc = execptionDesc.replace(exceptionName1, "");

						if (execptionDesc.contains(".")) {
							execptionDesc = execptionDesc
									.substring(execptionDesc.indexOf(":") + 1, execptionDesc.indexOf(".")).trim();

						} 
					} 
				} 
				else {
					if(stackTrace.contains("WebDriverException")) {
						execptionName = stackTrace.substring(stackTrace.indexOf("WebDriverException")).trim();
						execptionName = execptionName.substring(0, execptionName.indexOf(":")).trim();
						execptionDesc = stackTrace.substring(stackTrace.indexOf("WebDriverException")).trim();
						if(execptionDesc.contains("(") && !execptionDesc.contains("Remote browser") && !execptionDesc.contains("unknown error")) {
							execptionDesc = execptionDesc.substring(execptionDesc.indexOf(":"), execptionDesc.indexOf("(")).trim();
						} else {
							execptionDesc = execptionDesc.replace(execptionName+":", " ").trim();
							execptionDesc = execptionDesc.substring(0, execptionDesc.indexOf(":")).trim();	
						}
					} else {
						if (stackTrace.contains("at ")) {
							execptionDesc = stackTrace
									.substring(stackTrace.indexOf(execptionName) + execptionName.length() + 1,
											stackTrace.indexOf("at "))
									.trim();
						} else {
							execptionDesc = stackTrace
									.substring(stackTrace.indexOf(execptionName) + execptionName.length() + 1,
											stackTrace.indexOf("  ("))
									.trim();
						}
					}
				}

			} catch (Exception e) {
				if (stackTrace.indexOf(".java:") > stackTrace.indexOf("exception:") && stackTrace.indexOf("exception:") != -1) {

					execptionDesc = stackTrace.substring(stackTrace.indexOf("exception:") + 12,
							stackTrace.indexOf(".java:"));
					if(execptionDesc.contains("at "))
						execptionDesc = execptionDesc.substring(0, execptionDesc.indexOf("at ")).trim();

					if (execptionDesc.contains(":")) {
						exceptionName1 = execptionDesc.substring(0, execptionDesc.indexOf(":")+1).trim();

						execptionDesc = execptionDesc.replace(exceptionName1, "");

						if (execptionDesc.contains(".")) {
							execptionDesc = execptionDesc
									.substring(execptionDesc.indexOf(":") + 1, execptionDesc.indexOf(".")).trim();

						} 
					}
				}
			}

			if(execptionDesc.contains("couldn't")) {
				execptionDesc = execptionDesc.substring(execptionDesc.indexOf("'")+1, execptionDesc.length()).trim();
			}
			return execptionDesc.trim();

		}

	}

	public static LinkedHashSet<String> findAllMethods(String stackTraceFromComment) {

		LinkedHashSet<String> testSet = new LinkedHashSet<>();
		LinkedHashSet<String> methodNameSet = new LinkedHashSet<>();
		stackTraceFromComment = stackTraceFromComment.toLowerCase();

		String atSplit[] = stackTraceFromComment.split("at ");

		if (stackTraceFromComment.contains(".java:") && stackTraceFromComment.contains("at ")
				&& stackTraceFromComment.contains("(")) {
			for (String atString : atSplit) {

				String dotSplit[] = atString.split("\\.");

				if (dotSplit.length < 4 && atString.contains("(") && atString.contains(")")
						&& atString.contains(".java")) {

					testSet.add(atString);
				}

				for (String method : testSet) {
					String tempString = method.substring(method.indexOf(".") + 1, method.indexOf("("));

					methodNameSet.add(tempString);

				}

			}
		}
		return methodNameSet;
	}

}
