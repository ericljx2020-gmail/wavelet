import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    String[] comp = new String[100000];
    int idx = 0;
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String listing = "";
            if (idx == 0) return "Nothing added yet";
            for (int i = 0; i < idx; i++){
                listing += comp[i];
                listing += " ";
            }
            return listing;
        } 
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                // num += Integer.parseInt(parameters[1]);
                comp[idx++] = parameters[1];
                return String.format("Successfully added %s!", parameters[1]);
            }
        }else if (url.getPath().contains("/search")){
            String[] parameters = url.getQuery().split("=");
            String res = "";
            if (parameters[0].equals("s")){
                for (int i = 0; i < idx; i++){
                    if (comp[i].contains(parameters[1])){
                        res += comp[i];
                        res += " ";
                    }
                }
            }
            return res;
        }
        return "404 Not Found";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
