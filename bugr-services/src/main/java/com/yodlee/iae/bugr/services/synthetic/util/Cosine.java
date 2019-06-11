/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.util;


import java.util.Map;

import javax.inject.Named;



@Named
public class Cosine extends ShingleBased{

   
    public Cosine(final int k) {
        super(k);
    }

    
    public Cosine() {
        super();
    }

   
    public final double similarity(final String s1, final String s2) {
    	
    	if (s1.equals(s2)) {
            return 1;
        }

        if (s1.length() < getK() || s2.length() < getK()) {
            return 0;
        }
        
        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);
        
        return dotProduct(profile1, profile2)
                / (norm(profile1) * norm(profile2));
    }

    /**
     * Compute the norm L2 : sqrt(Sum_i( v_i²)).
     *
     * @param profile
     * @return L2 norm
     */
    private static double norm(final Map<String, Integer> profile) {
        double agg = 0;

        for (Map.Entry<String, Integer> entry : profile.entrySet()) {
            agg += 1.0 * entry.getValue() * entry.getValue();
        }

        return Math.sqrt(agg);
    }

    private static double dotProduct(
            final Map<String, Integer> profile1,
            final Map<String, Integer> profile2) {

        // Loop over the smallest map
        Map<String, Integer> small_profile = profile2;
        Map<String, Integer> large_profile = profile1;
        if (profile1.size() < profile2.size()) {
            small_profile = profile1;
            large_profile = profile2;
        }

        double agg = 0;
        for (Map.Entry<String, Integer> entry : small_profile.entrySet()) {
            Integer i = large_profile.get(entry.getKey());
            if (i == null) {
                continue;
            }
            agg += 1.0 * entry.getValue() * i;
        }

        return agg;
    }
}