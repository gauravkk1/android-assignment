package android.example.pathcrex.models;

public class FinishedMatchDetailModel {
     String team1, team2;
     String t1Flag, t2Flag;
     String score1, score2;
     String overs1, overs2;
     String winner;
     String matchNo;
     String date;
     int viewType;
     String t;
     String result;




     public FinishedMatchDetailModel(String team1, String team2, String t1Flag, String t2Flag, String score1, String score2, String overs1, String overs2, String winner, String matchNo, String date, int viewType, String t, String result) {
          this.team1 = team1;
          this.team2 = team2;
          this.t1Flag = t1Flag;
          this.t2Flag = t2Flag;
          this.score1 = score1;
          this.score2 = score2;
          this.overs1 = overs1;
          this.overs2 = overs2;
          this.winner = winner;
          this.matchNo = matchNo;
          this.date = date;
          this.viewType = viewType;
          this.t = t;
          this.result = result;


     }

     public FinishedMatchDetailModel(String date, int viewType) {
          this.date = date;
          this.viewType = viewType;
     }

     public String getTeam1() {
          return team1;
     }

     public void setTeam1(String team1) {
          this.team1 = team1;
     }

     public String getTeam2() {
          return team2;
     }

     public void setTeam2(String team2) {
          this.team2 = team2;
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

     public String getScore1() {
          return score1;
     }

     public void setScore1(String score1) {
          this.score1 = score1;
     }

     public String getT() {
          return t;
     }

     public void setT(String t) {
          this.t = t;
     }

     public String getScore2() {
          return score2;
     }

     public void setScore2(String score2) {
          this.score2 = score2;
     }

     public String getOvers1() {
          return overs1;
     }

     public void setOvers1(String overs1) {
          this.overs1 = overs1;
     }

     public String getOvers2() {
          return overs2;
     }

     public void setOvers2(String overs2) {
          this.overs2 = overs2;
     }

     public String getWinner() {
          return winner;
     }

     public void setWinner(String winner) {
          this.winner = winner;
     }

     public String getMatchNo() {
          return matchNo;
     }

     public void setMatchNo(String matchNo) {
          this.matchNo = matchNo;
     }

     public String getResult() {
          return result;
     }

     public String getDate() {
          return date;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public int getViewType() {
          return viewType;
     }

     public void setViewType(int viewType) {
          this.viewType = viewType;
     }
}
