
import org.apache.commons.exec.*;

import java.io.*;
import java.util.*;

public class ApacheCommonsExec
{

    public static void main(String[] args) throws Exception
    {
        CommandLine cmdLine = new CommandLine("python");
        cmdLine.addArgument("/my/python/script/script.py");
        // cmdLine.addArgument("/h");
        // cmdLine.addArgument("${file}");

        //  HashMap map = new HashMap();
        //  map.put("file", new File("invoice.pdf"));
        //  cmdLine.setSubstitutionMap(map);

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(1);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(new CollectingLogOutputStream());
        executor.execute(cmdLine, resultHandler);

        // some time later the result handler callback was invoked so we
        // can safely request the exit value
        resultHandler.waitFor();
    }
}

class CollectingLogOutputStream implements ExecuteStreamHandler {
    private final List<String> lines = new LinkedList<String>();

    public void setProcessInputStream(OutputStream outputStream) throws IOException {

    }

    public void setProcessErrorStream(InputStream inputStream) throws IOException {
        //InputStream is = ...; // keyboard, file or Internet
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line="";
        while( (line = br.readLine()) != null){
            //use lines whereever you want - for now just print on console
            System.out.println("error:"+line);
        }
    }

    public void setProcessOutputStream(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line="";
        while( (line = br.readLine()) != null){
            //use lines whereever you want - for now just print on console
            System.out.println("output:"+line);
        }
    }

    public void start() throws IOException {

    }

    public void stop() throws IOException {

    }
}
