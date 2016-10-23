
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ElementTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.SentenceTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analyzer {

    public static String analyze(String text) {
        ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        service.setUsernameAndPassword("44e15cf1-636c-4364-b0b5-c91e460b8983", "KW77yHLAatx1");//username and password change them if necessary

// Call the service and get the tone
        ToneAnalysis tone = service.getTone(text, null).execute();
        String result = parseAndReturn(tone);
        return (result);
    }

    public static String parseAndReturn(ToneAnalysis str) {
        String returnStr = "";

        ElementTone docTone = str.getDocumentTone();
        returnStr += "";
        {
            List<ToneCategory> toneCatList = docTone.getTones();
            for (ToneCategory toneCategory : toneCatList) {
                returnStr += "\n\n\n<h3><i>" + toneCategory.getName() + "</i></h3><br/><table>";
                List<ToneScore> toneList = toneCategory.getTones();
                for (ToneScore toneScore : toneList) {
                    returnStr += "\n\n<tr>";
                    toneScore.getName();
                    Double d = toneScore.getScore() * 100;
                    String strDouble = String.format("%05.2f", d);
                    String name = String.format("%-16s", toneScore.getName());
                    returnStr += "\n<td>" + name + "</td>";
                    returnStr += "\t<td>" + strDouble + "%" + "</td></tr>";
                }
                returnStr += "</table></br>";
            }
        }
        /*returnStr+="\n\n\nSentence Category\n\n\n\n";
        List<SentenceTone> sentToneList = str.getSentencesTone();
        for(SentenceTone sentenceTone : sentToneList)
        {   
            returnStr+="\n\n\n"+sentenceTone.getText();
            List<ToneCategory> toneCatList = sentenceTone.getTones();
            for(ToneCategory toneCategory :toneCatList )
            {
                returnStr+="\n\n\n"+toneCategory.getName();
                List<ToneScore> toneList = toneCategory.getTones();
                for(ToneScore toneScore : toneList)
                {
                    returnStr+="\n\n";
                    toneScore.getName();
                    Double d = toneScore.getScore()*100;
                    String strDouble = String.format("%06.2f",d);
                    
                    String name = String.format("%-16s",toneScore.getName());
                    returnStr+="\n"+name;
                    returnStr+="\t"+strDouble+"%";
                }
            }
        }*/
        return returnStr;
    }

    public static String greatestTrait(String text) {
        ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        service.setUsernameAndPassword("44e15cf1-636c-4364-b0b5-c91e460b8983", "KW77yHLAatx1");//username and password change them if necessary
        ToneAnalysis tone = service.getTone(text, null).execute();
        ElementTone docTone = tone.getDocumentTone();
        List<ToneCategory> toneCategory = docTone.getTones();
        ToneCategory emotion = toneCategory.get(0);
        List<ToneScore> toneScore = emotion.getTones();
        ToneScore temp = toneScore.get(0);
        for (ToneScore toneA : toneScore) {
            if (temp.getScore() < toneA.getScore()) {
                temp = toneA;
            }
        }
        return temp.getName();

    }
    /*public static void postMessageToLobby(String str)
    {
            String outputString;
            String apiEndPoint = "https://api.flock.co/hooks/sendMessage/5b363289-f013-4760-aea6-2ef2f6fc292c";
            String command = "curl -X POST "+ apiEndPoint +" -H  \"Content-Type: application/json\" -d \'{ \"text\": \" "+str+"\"}\' ";
            Process curlProc;
	    try {
	        curlProc = Runtime.getRuntime().exec(command);
	        curlProc.waitFor();

	        //Thread.sleep(10000);
	        DataInputStream curlIn = new DataInputStream(
	                curlProc.getInputStream());

	        while ((outputString = curlIn.readLine()) != null) {
	        	
	            System.out.println(outputString);
	        }

	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
}
