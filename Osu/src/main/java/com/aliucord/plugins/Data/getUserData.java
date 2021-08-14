/*
 * Wolf's Aliucord Plugins
 * Copyright (C) 2021 Wolfkid200444
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.aliucord.plugins.Data;

import java.util.List;

public class getUserData {
    public List<Data> custom;

    public static class Data {
        public String format_join_date;
        public String format_last_visit;
        public String pp_rank;
        public String hit_accuracy;
        public String playcount;
        public String pp_raw;
        public String pp_country_rank;
        public String time_played;
        public String playstyles;
    }
    public Boolean is_online;
    public String username;
    public String country_code;
    public String user_profile;
    public String avatar_url;
}