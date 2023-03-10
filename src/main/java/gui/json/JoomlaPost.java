package gui.json;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

public class JoomlaPost {
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getArticletext() {
        return articletext;
    }

    public void setArticletext(String articletext) {
        this.articletext = articletext;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMetadesc() {
        return metadesc;
    }

    public void setMetadesc(String metadesc) {
        this.metadesc = metadesc;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String articletext;
    private Integer catid;
    private String language = "*";
    private String metadesc;
    private String metakey;
    private String title;

    public JoomlaPost(String articletext, String title) {
        this.articletext = articletext;
        this.title = title;
        this.alias = translitor(title);
        this.catid = 1;
        this.metadesc = "";
        this.metakey = "";

    }

    public JoomlaPost(String articletext, Integer catid, String title) {
        this.articletext = articletext;
        this.catid = catid;
        this.title = title;
        this.alias = translitor(title);
        this.metadesc = "";
        this.metakey = "";
    }

    public JoomlaPost(String articletext, Integer catid, String metadesc, String title) {
        this.articletext = articletext;
        this.catid = catid;
        this.metadesc = metadesc;
        this.title = title;
        this.alias = translitor(title);
        this.metakey = "";
    }

    public JoomlaPost(String articletext, Integer catid, String metadesc, String metakey, String title) {
        this.articletext = articletext;
        this.catid = catid;
        this.metadesc = metadesc;
        this.metakey = metakey;
        this.title = title;
        this.alias = translitor(title);
    }

    public JoomlaPost(String alias, String articletext, Integer catid, String language, String metadesc, String metakey, String title) {
        this.alias = alias;
        this.articletext = articletext;
        this.catid = catid;
        this.language = language;
        this.metadesc = metadesc;
        this.metakey = metakey;
        this.title = title;
    }

    public static String translitor(String st){
        st = st.toLowerCase();
        char [] charMass = st.toCharArray();
        for(int i = 0; i < charMass.length; i++){
            switch (charMass[i]) {
                case '??' -> charMass[i] = 'a';
                case '??' -> charMass[i] = 'b';
                case '??' -> charMass[i] = 'v';
                case '??' -> charMass[i] = 'g';
                case '??' -> charMass[i] = 'd';
                case '??', '??' -> charMass[i] = 'e';
                case '??', '??' -> charMass[i] = 'o';
                case '??' -> charMass[i] = 'z';
                case '??' -> charMass[i] = 'z';
                case '??', '??' -> charMass[i] = 'i';
                case '??' -> charMass[i] = 'i';
                case '??' -> charMass[i] = 'k';
                case '??' -> charMass[i] = 'l';
                case '??' -> charMass[i] = 'm';
                case '??' -> charMass[i] = 'n';
                case '??' -> charMass[i] = 'p';
                case '??' -> charMass[i] = 'r';
                case '??' -> charMass[i] = 'c';
                case '??' -> charMass[i] = 't';
                case '??' -> charMass[i] = 'y';
                case '??' -> charMass[i] = 'f';
                case '??' -> charMass[i] = 'h';
                case '??', '??' -> charMass[i] = 'c';
                case '??', '??' -> charMass[i] = 's';
                case '??', '??' -> charMass[i] = '_';
                case '??' -> charMass[i] = 'u';
                case '??' -> charMass[i] = 'y';
            }
        }
        return String.valueOf(charMass);
    }
    public static boolean checkParentElementType(HTML.Tag tag , JEditorPane pane){
        int p = pane.getCaretPosition();
        HTMLDocument doc = (HTMLDocument) pane.getDocument();
        Element e = doc.getParagraphElement(p);
        String tagString = tag.toString();
        if(e.getName().equalsIgnoreCase(tagString))
        {
            return true;
        }
        do
        {
            if((e = e.getParentElement()).getName().equalsIgnoreCase(tagString))
            {
                return true;

            }
        } while(!(e.getName().equalsIgnoreCase("html")));
        return false;
    }
}
