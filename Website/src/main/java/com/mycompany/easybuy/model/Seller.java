package com.mycompany.easybuy.model;

public class Seller {
    private int id;
    private int memberId;
    private String gstId;
    private boolean verified;
    private String businessName;
    private String storeDesc;

    public Seller(int id, int memberId, String gstId, boolean verified, String businessName, String storeDesc) {
        this.id = id;
        this.memberId = memberId;
        this.gstId = gstId;
        this.verified = verified;
        this.businessName = businessName;
        this.storeDesc = storeDesc;
    }

    public Seller(int memberId, String gstId, boolean verified, String businessName, String storeDesc) {
        this.memberId = memberId;
        this.gstId = gstId;
        this.verified = verified;
        this.businessName = businessName;
        this.storeDesc = storeDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    @Override
    public String toString() {
        return "Seller{" + "id=" + id + ", memberId=" + memberId + ", gstId=" + gstId + ", verified=" + verified + ", businessName=" + businessName + ", storeDesc=" + storeDesc + '}';
    }
}
