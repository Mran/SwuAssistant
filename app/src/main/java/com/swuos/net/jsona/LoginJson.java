package com.swuos.net.jsona;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张孟尧 on 2016/8/30.
 */
public class LoginJson {
    /**
     * success : true
     * data : {"getUserInfoByUserNameResponse":{"return":{"success":true,"info":{"id":"222014327012167","attributes":{"ACPNAME":"5ZCR56S85rW3","e_rdn":"QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"eGxoNDQy","ACPSCHOOLEMAIL":"eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzI3","tgt":"VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDE0MzI3MDEyMTY3"}}}}}
     */

    private boolean success;
    /**
     * getUserInfoByUserNameResponse : {"return":{"success":true,"info":{"id":"222014327012167","attributes":{"ACPNAME":"5ZCR56S85rW3","e_rdn":"QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"eGxoNDQy","ACPSCHOOLEMAIL":"eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzI3","tgt":"VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDE0MzI3MDEyMTY3"}}}}
     */

    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * return : {"success":true,"info":{"id":"222014327012167","attributes":{"ACPNAME":"5ZCR56S85rW3","e_rdn":"QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"eGxoNDQy","ACPSCHOOLEMAIL":"eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzI3","tgt":"VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDE0MzI3MDEyMTY3"}}}
         */

        private GetUserInfoByUserNameResponseBean getUserInfoByUserNameResponse;

        public GetUserInfoByUserNameResponseBean getGetUserInfoByUserNameResponse() {
            return getUserInfoByUserNameResponse;
        }

        public void setGetUserInfoByUserNameResponse(GetUserInfoByUserNameResponseBean getUserInfoByUserNameResponse) {
            this.getUserInfoByUserNameResponse = getUserInfoByUserNameResponse;
        }

        public static class GetUserInfoByUserNameResponseBean {
            /**
             * success : true
             * info : {"id":"222014327012167","attributes":{"ACPNAME":"5ZCR56S85rW3","e_rdn":"QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"eGxoNDQy","ACPSCHOOLEMAIL":"eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzI3","tgt":"VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDE0MzI3MDEyMTY3"}}
             */

            @SerializedName("return")
            private ReturnBean returnX;

            public ReturnBean getReturnX() {
                return returnX;
            }

            public void setReturnX(ReturnBean returnX) {
                this.returnX = returnX;
            }

            public static class ReturnBean {
                private boolean success;
                /**
                 * id : 222014327012167
                 * attributes : {"ACPNAME":"5ZCR56S85rW3","e_rdn":"QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==","ACPNICKNAME":"eGxoNDQy","ACPSCHOOLEMAIL":"eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=","ACPORGDN":"MzI3","tgt":"VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==","ACPUSERGROUPDN":"Ymtz","ACPUID":"MjIyMDE0MzI3MDEyMTY3"}
                 */

                private InfoBean info;

                public boolean isSuccess() {
                    return success;
                }

                public void setSuccess(boolean success) {
                    this.success = success;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    private String id;
                    /**
                     * ACPNAME : 5ZCR56S85rW3
                     * e_rdn : QUNQVUlEPTIyMjAxNDMyNzAxMjE2NyxDT05UQUlORVJJRD1wZXJzb24sY249dXNlcg==
                     * ACPNICKNAME : eGxoNDQy
                     * ACPSCHOOLEMAIL : eGxoNDQyQGVtYWlsLnN3dS5lZHUuY24=
                     * ACPORGDN : MzI3
                     * tgt : VEdULTc2MzEtY3ZoSEc1Y1V6TEUxUjB4T2daZU9sTjBkUGR0NVJPY1pSUk5DVjRTYlpCTE1lekc5NmYtaHR0cDovLzIyMi4xOTguMTIwLjIwODo4MDgyL2Nhcw==
                     * ACPUSERGROUPDN : Ymtz
                     * ACPUID : MjIyMDE0MzI3MDEyMTY3
                     */

                    private AttributesBean attributes;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public AttributesBean getAttributes() {
                        return attributes;
                    }

                    public void setAttributes(AttributesBean attributes) {
                        this.attributes = attributes;
                    }

                    public static class AttributesBean {
                        private String ACPNAME;
                        private String e_rdn;
                        private String ACPNICKNAME;
                        private String ACPSCHOOLEMAIL;
                        private String ACPORGDN;
                        private String tgt;
                        private String ACPUSERGROUPDN;
                        private String ACPUID;

                        public String getACPNAME() {
                            return ACPNAME;
                        }

                        public void setACPNAME(String ACPNAME) {
                            this.ACPNAME = ACPNAME;
                        }

                        public String getE_rdn() {
                            return e_rdn;
                        }

                        public void setE_rdn(String e_rdn) {
                            this.e_rdn = e_rdn;
                        }

                        public String getACPNICKNAME() {
                            return ACPNICKNAME;
                        }

                        public void setACPNICKNAME(String ACPNICKNAME) {
                            this.ACPNICKNAME = ACPNICKNAME;
                        }

                        public String getACPSCHOOLEMAIL() {
                            return ACPSCHOOLEMAIL;
                        }

                        public void setACPSCHOOLEMAIL(String ACPSCHOOLEMAIL) {
                            this.ACPSCHOOLEMAIL = ACPSCHOOLEMAIL;
                        }

                        public String getACPORGDN() {
                            return ACPORGDN;
                        }

                        public void setACPORGDN(String ACPORGDN) {
                            this.ACPORGDN = ACPORGDN;
                        }

                        public String getTgt() {
                            return tgt;
                        }

                        public void setTgt(String tgt) {
                            this.tgt = tgt;
                        }

                        public String getACPUSERGROUPDN() {
                            return ACPUSERGROUPDN;
                        }

                        public void setACPUSERGROUPDN(String ACPUSERGROUPDN) {
                            this.ACPUSERGROUPDN = ACPUSERGROUPDN;
                        }

                        public String getACPUID() {
                            return ACPUID;
                        }

                        public void setACPUID(String ACPUID) {
                            this.ACPUID = ACPUID;
                        }
                    }
                }
            }
        }
    }
}
