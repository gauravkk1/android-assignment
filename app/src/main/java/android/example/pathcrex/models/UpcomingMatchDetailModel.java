package android.example.pathcrex.models;

public class UpcomingMatchDetailModel {
    String t1, t2;
    String t1Flag, t2Flag;
    String matchNo;
    String date;
    String rate1, rate2,rateTeam;
    String t;
    String clubDate;
    long timeStamp;

    private int viewType;
    private int odds;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    public UpcomingMatchDetailModel(String clubDate, String t1, String t2, String t1Flag, String t2Flag, String matchNo, String date, String rate1, String rate2, String rateTeam, String t, int viewType, int odds, long timeStamp) {
        this.t1 = t1;
        this.t2 = t2;
        this.t1Flag = t1Flag;
        this.t2Flag = t2Flag;
        this.matchNo = matchNo;
        this.date = date;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rateTeam = rateTeam;
        this.t =  t;
        this.viewType = viewType;
        this.odds = odds;
        this.clubDate = clubDate;
        this.timeStamp = timeStamp;
    }

    public UpcomingMatchDetailModel(String clubDate, String t1, String t2, String t1Flag, String t2Flag, String matchNo, String date, String t, int viewType, int odds, long timeStamp) {
        this.t1 = t1;
        this.t2 = t2;
        this.t1Flag = t1Flag;
        this.t2Flag = t2Flag;
        this.matchNo = matchNo;
        this.date = date;
        this.t = t;
        this.viewType = viewType;
        this.odds = odds;
        this.timeStamp = timeStamp;
        this.clubDate = clubDate;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UpcomingMatchDetailModel(int viewType, int odds) {
        this.viewType = viewType;
        this.odds = odds;
    }

    public UpcomingMatchDetailModel(String clubDate, int viewType) {
        this.clubDate = clubDate;
        this.viewType = viewType;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT1Flag() {
        return t1Flag;
    }

    public void setT1Flag(String t1Flag) {
        this.t1Flag = t1Flag;
    }

    public String getT2Flag() {
        return t2Flag;
    }

    public void setT2Flag(String t2Flag) {
        this.t2Flag = t2Flag;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate1() {
        return rate1;
    }

    public void setRate1(String rate1) {
        this.rate1 = rate1;
    }

    public String getRate2() {
        return rate2;
    }

    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }

    public String getRateTeam() {
        return rateTeam;
    }

    public void setRateTeam(String rateTeam) {
        this.rateTeam = rateTeam;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getClubDate() {
        return clubDate;
    }

    public void setClubDate(String clubDate) {
        this.clubDate = clubDate;
    }

    public boolean hasClubDate() {
        return this.clubDate != null;
    }

    public boolean hasDate(){
        return this.date != null;
    }


}
