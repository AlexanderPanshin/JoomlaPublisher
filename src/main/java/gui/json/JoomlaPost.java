package gui.json;

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
                case 'а' -> charMass[i] = 'a';
                case 'б' -> charMass[i] = 'b';
                case 'в' -> charMass[i] = 'v';
                case 'г' -> charMass[i] = 'g';
                case 'д' -> charMass[i] = 'd';
                case 'е', 'э' -> charMass[i] = 'e';
                case 'ё', 'о' -> charMass[i] = 'o';
                case 'ж' -> charMass[i] = 'z';
                case 'з' -> charMass[i] = 'z';
                case 'и', 'ы' -> charMass[i] = 'i';
                case 'й' -> charMass[i] = 'i';
                case 'к' -> charMass[i] = 'k';
                case 'л' -> charMass[i] = 'l';
                case 'м' -> charMass[i] = 'm';
                case 'н' -> charMass[i] = 'n';
                case 'п' -> charMass[i] = 'p';
                case 'р' -> charMass[i] = 'r';
                case 'с' -> charMass[i] = 'c';
                case 'т' -> charMass[i] = 't';
                case 'у' -> charMass[i] = 'y';
                case 'ф' -> charMass[i] = 'f';
                case 'х' -> charMass[i] = 'h';
                case 'ц', 'ч' -> charMass[i] = 'c';
                case 'ш', 'щ' -> charMass[i] = 's';
                case 'ъ', 'ь' -> charMass[i] = '_';
                case 'ю' -> charMass[i] = 'u';
                case 'я' -> charMass[i] = 'y';
            }
        }
        return String.valueOf(charMass);
    }
}
