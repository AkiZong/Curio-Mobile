package example.com.curio;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    public static String test;

    // project list
    public static Project_list projectlist;
    public static JSONArray projectlist_Jarray;
    public static int projectlist_num;
    public static int project_id;
    public static JSONArray project_team_Jarray;
    public static int project_team_id;
    public static int project_team_num;

    // question list
    public static Question_list questionlist;
    public static JSONArray questionlist_Jarray;
    public static int questionlist_num;
    public static int question_id;

    // team
    public static Team team;
    public static JSONObject team_Jobject;
    public static JSONArray team_Jarray;
    public static int teamlist_num;
    public static int team_id;
    public static boolean find_team_member = false;
    public static List<String> team_member = new ArrayList<>();

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    public static final List<DummyItem> refreshITEMS = new ArrayList<DummyItem>();

    static {
        projectlist = new Project_list();
        questionlist = new Question_list();
        team = new Team();

        try {
            projectlist_Jarray = projectlist.execute("a").get();
        }catch (ExecutionException e){}
        catch (InterruptedException e){};

        try {
            questionlist_Jarray = questionlist.execute("b").get();
        }catch (ExecutionException e){}
        catch (InterruptedException e){};

        try {
            team_Jobject = team.execute("c").get();
        }catch (ExecutionException e) {}
        catch (InterruptedException e){};

        try {
            team_Jarray = team_Jobject.getJSONArray("results");
        } catch (JSONException e){}

        projectlist_num = projectlist_Jarray.length();
        questionlist_num = questionlist_Jarray.length();
        teamlist_num = team_Jarray.length();

        // create project list in master view
        for (int i = 0; i < projectlist_num; i++) {
            try {
                int idnum = projectlist_Jarray.getJSONObject(i).getInt("id");
                addItem(createDummyItem(idnum));
            } catch (JSONException e){}
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        try{
            String a = projectlist_Jarray.getJSONObject(position-1).getString("name");
            String back_url = projectlist_Jarray.getJSONObject(position-1).getString("avatar");
            return new DummyItem(String.valueOf(position),a, makeDetails(position),back_url);
        }catch (JSONException e){
            return new DummyItem(String.valueOf(position),"FAILLLL", makeDetails(position),"FAILLL");
        }
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();

        // ABOUT SECTION ------------------------------------------
        builder.append("\n" + "** ABOUT **");
        try {
            String a = projectlist_Jarray.getJSONObject(position - 1).getString("description");
            builder.append("\n" + a);
            builder.toString();
        } catch (JSONException e) {
            return "Loading failed: About section.";
        }

        // CONTRIBUTE SECTION ------------------------------------------
        builder.append("\n"+"\n");
        builder.append("\n" + "** CONTRIBUTE **");
        for (int i = 0; i < projectlist_num; i++) {
            for (int j = 0; j < questionlist_num; j++) {
                try {
                    project_id = projectlist_Jarray.getJSONObject(i).getInt("id");
                    question_id = questionlist_Jarray.getJSONObject(j).getInt("id");
                } catch (JSONException e) {}
                if (project_id == question_id) {
                    try {
                        String a = questionlist_Jarray.getJSONObject(position-1).getString("question");
                        String b = questionlist_Jarray.getJSONObject(position-1).getString("motivation");
                        builder.append("\n" + a);
                        builder.append("\n");
                        builder.append("\n" + b);
                        builder.toString();
                    } catch (JSONException e) {return "Loading failed: Contribute section.";}
                }
                break;
            }
        }

        // TEAM SECTION ------------------------------------------
        builder.append("\n"+"\n");
        builder.append("\n" + "** TEAM **");

        try {
            project_team_Jarray = projectlist_Jarray.getJSONObject(position-1).getJSONArray("team");
        } catch (JSONException e) {}

        project_team_num = project_team_Jarray.length();

        for (int i = 0; i < project_team_num; i++) {
            try {
                project_team_id = project_team_Jarray.getJSONObject(i).getInt("id");
            } catch (JSONException e) {}
            for (int j = 0; j < teamlist_num; j++) {
                try {
                    team_id = team_Jarray.getJSONObject(j).getInt("id");
                } catch (JSONException e) {}
                if (project_team_id == team_id) {
                    /*
                    ImageView image = new ImageView(this);
                    image.setBackgroundResource(R.drawable.refresh);
                    item_detail_container.addView(image);
                    */
                    try {
                        String a = team_Jarray.getJSONObject(j).getString("nickname");
                        String b = team_Jarray.getJSONObject(j).getString("title");
                        String c = team_Jarray.getJSONObject(j).getString("bio");
                        if (a != "") {builder.append("\n"+a);}
                        if (b != "") {builder.append("\n"+b);}
                        if (c != "") {builder.append("\n"+c);}
                        builder.append("\n");
                    } catch (JSONException e) {return "Loading failed: Contribute section.";}
                    break;
                }
            }
        }
        return builder.toString();
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        public final String back_url;

        public DummyItem(String id, String content, String details, String back_url) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.back_url = back_url;
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public static void refresh(){
        ITEMS.clear();
        ITEM_MAP.clear();
        projectlist = new Project_list();
        questionlist = new Question_list();
        team = new Team();

        try {
            projectlist_Jarray = projectlist.execute("a").get();
        }catch (ExecutionException e){}
        catch (InterruptedException e){};

        try {
            questionlist_Jarray = questionlist.execute("b").get();
        }catch (ExecutionException e){}
        catch (InterruptedException e){};

        try {
            team_Jobject = team.execute("c").get();
        }catch (ExecutionException e) {}
        catch (InterruptedException e){};

        try {
            team_Jarray = team_Jobject.getJSONArray("results");
        } catch (JSONException e){}

        projectlist_num = projectlist_Jarray.length();
        questionlist_num = questionlist_Jarray.length();
        teamlist_num = team_Jarray.length();

        // create project list in master view
        for (int i = 0; i < projectlist_num; i++) {
            try {
                int idnum = projectlist_Jarray.getJSONObject(i).getInt("id");
                addItem(createDummyItem(idnum));
            } catch (JSONException e){}
        }
    }

    public static void search(String tmp){
        ITEMS.clear();
        ITEM_MAP.clear();
        //projectlist = new Project_list();
        //questionlist = new Question_list();
        //team = new Team();
        Search_list searchList = new Search_list();
        searchList.set_se(tmp);


        try {
            projectlist_Jarray = searchList.execute("a").get();
        }catch (ExecutionException e){}
        catch (InterruptedException e){};


        projectlist_num = projectlist_Jarray.length();
        //questionlist_num = questionlist_Jarray.length();
        //teamlist_num = team_Jarray.length();

        // create project list in master view
        for (int i = 0; i < projectlist_num; i++) {
            try {
                int idnum = projectlist_Jarray.getJSONObject(i).getInt("id");
                addItem(createDummyItem(i+1));
            } catch (JSONException e){}
        }
    }



}
