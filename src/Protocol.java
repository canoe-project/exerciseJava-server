/*
프로토콜을 분석,처리하는 클래스

request -> req
response -> res
*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Protocol {

    private String type = null;
    private String message = null;
    private String cid = null;
    private int num_req = 0;

    public enum statusCode{
        Hi(100),
        CurrentTime(130),
        ConnectionTime(150),
        ClientList(200),
        Quit(250),
        Error(300),
        Message(500);

        private int code;
        statusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    };

    Protocol(){

    }

    Protocol(String type, String message, String cid, int num_req){
        this.type = type;
        this.message = message;
        this.cid = cid;
        this.num_req = num_req;
    }

    public String getCid(){
        return this.cid;
    }

    public String getMessage(){return this.message;}

    public statusCode reqCheck(){
        for (statusCode i:statusCode.values()){
            if (i.toString().equals(this.message)){
                    return i;
            }
        }
            return statusCode.Message;
    }

    public static String resFormat(statusCode code, String message){
        return "response" + "///"+ code.code+ "///" +message+ "///";
    }

    public static Protocol reqParser(String req){
        String reg = "^(?<type>request|response)\\/{3}(?<message>.+)\\/{3}CID:(?<CID>\\w+)\\/{3}Num_Req:(?<counter>\\d+)\\/{3}END_MSG$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(req);

        if (m.matches())
        {
            return new Protocol(m.group("type"), m.group("message"), m.group("CID"), Integer.parseInt(m.group("counter")));
        }
        else{
            return null;
        }
    }
}

/*
enum 참조
https://www.nextree.co.kr/p11686/
https://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
https://johngrib.github.io/wiki/java-enum/
https://www.baeldung.com/java-enum-iteration
*/
/*
https://medium.com/@originerd/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D-%EC%A2%80-%EB%8D%94-%EA%B9%8A%EC%9D%B4-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0-5bd16027e1e0
*/
