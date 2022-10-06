import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: am Array List of String to monitor the
    // added strings.
    ArrayList<String> items;

    public Handler() {
        this.items = new ArrayList<String>();
    }

    public String handleRequest(URI url) {
        System.out.println("Path: " + url.getPath());
        if (url.getPath().equals("/")) {
            String temp = "";
            for (int i = 0; i < this.items.size(); i += 1) {
                temp += this.items.get(i);
                temp += '\n';
            }
            return temp;
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                this.items.add(parameters[1]);
                return String.format("A new String was added %s", parameters[1]);
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String temp = "";
                for (int i = 0; i < this.items.size(); i += 1) {
                    if (this.items.get(i).contains(parameters[1])) {
                        temp += this.items.get(i);
                        temp += '\n';
                    }
                }
                return String.format(temp);
            }
        }
        return "404 Not Found";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}