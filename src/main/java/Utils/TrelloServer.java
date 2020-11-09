package Utils;

public class TrelloServer {
        public String getSID() {
            return SID;
        }

        public String getToken() {
            return Token;
        }

        //String BaseURL;
        String Token;
        String SID;

        public TrelloServer(String SID, String Token){
            this.SID = SID;
            this.Token = Token;
            //this.AccountURL = AccountBaseURL;
        }
}

