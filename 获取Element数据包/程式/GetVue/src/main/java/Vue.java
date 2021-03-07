import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

//执行时长5分钟左右
public class Vue {
    //用于收集异常url数据
    static StringBuilder stringBuilder = new StringBuilder();
    public static String root = "H:/GetVue/target/classes/element";
    public static String browerUrl="https://unpkg.com/browse/element-ui@2.15.1/";
    public static String resourseUrl="https://unpkg.com/element-ui@2.15.1/";

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        JSONObject jsonObject = GetJsonObject(browerUrl);
        GetList(jsonObject);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)/1000);

        System.err.println(stringBuilder);
    }

    /*
    * 由于Element的目录信息存放在js文件的window.__DATA__属性中
    * 所以通过Jsoup来解析window.__DATA__
    * */
    public static JSONObject GetJsonObject(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(20000).get();
            Elements elements = document.getElementsByTag("script");
            for (Element e: elements) {
                if(e.childNodeSize()==1){
                    String str = e.childNode(0).toString();
                    //此处只需要解析directory类型的目录即可
                    if(str.contains("window.__DATA__ ")&&url.endsWith("/")){
                        JSONObject jsonObject = new JSONObject(str.substring(str.indexOf("{")));
                        JSONObject jsonArray = jsonObject.getJSONObject("target").getJSONObject("details");
                        return jsonArray;
                    }
                }
            }
        } catch (Exception e) {
            stringBuilder.append(url).append(",");
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 从window.__DATA__解析得到的JSONObject对象中获取当前目录节点的path以及子目录信息url
     * */
    public static void GetList(JSONObject jsonObject) throws IOException {
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String key = iterator.next().toString();
                JSONObject jo = jsonObject.getJSONObject(key);
                System.out.println(jo.toString());
                if("directory".equals(jo.get("type"))) {
                    String filePath = root+jo.get("path");
                    Files.createDirectories(Paths.get(filePath));
                    GetList(GetJsonObject("https://unpkg.com/browse/element-ui@2.15.1/"+jo.get("path").toString().substring(0)+"/"));
                }else{
                    String filePath = root+jo.get("path");
                    String url = "https://unpkg.com/browse/element-ui@2.15.1/"+jo.get("path").toString().substring(0);
                    ReadFile(filePath,url);
                }
            }
    }


    /**
     * 通过HttpConnection访问文件资源信息，并从响应数据的body中获取数据
     * */
    public static void ReadFile(String path,String url){
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            url = url.replaceAll(browerUrl,resourseUrl);
            //请求文件资源并获得response对象
            Connection.Response response = Jsoup.connect(url).timeout(10000).ignoreContentType(true).execute();
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            //由于element目录中包含ttf类型的文件，无法直接通过字符串读取，所有通过bodyStream获取
            if("font/ttf".equals(response.contentType())){
                BufferedInputStream bufferedInputStream=response.bodyStream();
                byte[] bytes = new byte[1024];
                while (bufferedInputStream.read(bytes)!=-1){
                    fileOutputStream.write(bytes);
                }
                fileOutputStream.flush();
            }else {
                PrintWriter pw = new PrintWriter(fileOutputStream);
                pw.write(response.body());
                pw.flush();
            }
        }catch (Exception ex){
            System.err.println(url);
            System.err.println(ex);
        }
    }
}