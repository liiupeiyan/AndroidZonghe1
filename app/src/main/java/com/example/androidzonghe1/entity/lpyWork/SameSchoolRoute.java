package com.example.androidzonghe1.entity.lpyWork;

public class SameSchoolRoute {
    private String school;
    private String communityFirst;
    private double distanceCommunityFirst;
    private String communitySecond;
    private double distanceCommunitySecond;
    private int peopleNow;
    private int peopleTotal;
    private String routeState;

    public SameSchoolRoute(String school, String communityFirst, double distanceCommunityFirst, String communitySecond, double distanceCommunitySecond, int peopleNow, int peopleTotal,String routeState) {
        this.school = school;
        this.communityFirst = communityFirst;
        this.distanceCommunityFirst = distanceCommunityFirst;
        this.communitySecond = communitySecond;
        this.distanceCommunitySecond = distanceCommunitySecond;
        this.peopleNow = peopleNow;
        this.peopleTotal = peopleTotal;
        this.routeState = routeState;
    }

    public SameSchoolRoute() {
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCommunityFirst() {
        return communityFirst;
    }

    public void setCommunityFirst(String communityFirst) {
        this.communityFirst = communityFirst;
    }

    public double getDistanceCommunityFirst() {
        return distanceCommunityFirst;
    }

    public void setDistanceCommunityFirst(double distanceCommunityFirst) {
        this.distanceCommunityFirst = distanceCommunityFirst;
    }

    public String getCommunitySecond() {
        return communitySecond;
    }

    public void setCommunitySecond(String communitySecond) {
        this.communitySecond = communitySecond;
    }

    public double getDistanceCommunitySecond() {
        return distanceCommunitySecond;
    }

    public void setDistanceCommunitySecond(double distanceCommunitySecond) {
        this.distanceCommunitySecond = distanceCommunitySecond;
    }

    public int getPeopleNow() {
        return peopleNow;
    }

    public void setPeopleNow(int peopleNow) {
        this.peopleNow = peopleNow;
    }

    public int getPeopleTotal() {
        return peopleTotal;
    }

    public void setPeopleTotal(int peopleTotal) {
        this.peopleTotal = peopleTotal;
    }

    public String getRouteState() {
        return routeState;
    }

    public void setRouteState(String routeState) {
        this.routeState = routeState;
    }

    @Override
    public String toString() {
        return "SameSchoolRoute{" +
                "school='" + school + '\'' +
                ", communityFirst='" + communityFirst + '\'' +
                ", distanceCommunityFirst=" + distanceCommunityFirst +
                ", communitySecond='" + communitySecond + '\'' +
                ", distanceCommunitySecond=" + distanceCommunitySecond +
                ", peopleNow=" + peopleNow +
                ", peopleTotal=" + peopleTotal +
                ", routeState='" + routeState + '\'' +
                '}';
    }
}
