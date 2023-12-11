package com.trantanthanh.ex2;

import com.google.gson.annotations.SerializedName;

public class StatisticsResponse {
    @SerializedName("response")
    private CovidData[] response;

    public CovidData[] getResponse() {
        return response;
    }

    public void setResponse(CovidData[] response) {
        this.response = response;
    }
    class CovidData {
        private String continent;
        private String country;
        private String population;
        private Cases cases;
        private Deaths deaths;
        private Tests tests;
        private String day;
        private String time;

        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        public Cases getCases() {
            return cases;
        }

        public void setCases(Cases cases) {
            this.cases = cases;
        }

        public Deaths getDeaths() {
            return deaths;
        }

        public void setDeaths(Deaths deaths) {
            this.deaths = deaths;
        }

        public Tests getTests() {
            return tests;
        }

        public void setTests(Tests tests) {
            this.tests = tests;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        class Cases {
            @SerializedName("new")
            private String newCases;
            private long active;
            private long critical;
            private long recovered;
            @SerializedName("1M_pop")
            private String populationPerMillion;
            private long total;

            public String getNewCases() {
                return newCases;
            }

            public void setNewCases(String newCases) {
                this.newCases = newCases;
            }

            public long getActive() {
                return active;
            }

            public void setActive(long active) {
                this.active = active;
            }

            public long getCritical() {
                return critical;
            }

            public void setCritical(long critical) {
                this.critical = critical;
            }

            public long getRecovered() {
                return recovered;
            }

            public void setRecovered(long recovered) {
                this.recovered = recovered;
            }

            public String getPopulationPerMillion() {
                return populationPerMillion;
            }

            public void setPopulationPerMillion(String populationPerMillion) {
                this.populationPerMillion = populationPerMillion;
            }

            public long getTotal() {
                return total;
            }

            public void setTotal(long total) {
                this.total = total;
            }

        }

        class Deaths {
            @SerializedName("new")
            private String newDeaths;
            @SerializedName("total")
            private long total;

            public String getNewDeaths() {
                return newDeaths;
            }

            public void setNewDeaths(String newDeaths) {
                this.newDeaths = newDeaths;
            }

            public long getTotal() {
                return total;
            }

            public void setTotal(long total) {
                this.total = total;
            }
        }

        class Tests {
            @SerializedName("1M_pop")
            private String populationPerMillion;
            private long total;

            public String getPopulationPerMillion() {
                return populationPerMillion;
            }

            public void setPopulationPerMillion(String populationPerMillion) {
                this.populationPerMillion = populationPerMillion;
            }

            public long getTotal() {
                return total;
            }

            public void setTotal(long total) {
                this.total = total;
            }

        }
    }


}

