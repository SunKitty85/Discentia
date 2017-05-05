// test
package SqliteHelper;

/**
 * Created by moltox on 06.03.2017.
 */

public class Card {
    int id;
    int cardId;
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    String releaseDate;
    String img_path;
    String category;


    String subject;

    // constructors ...
    public Card()  {
        this.question = "";
        this.answer1 = "";
        this.answer2 = "";
        this.answer3 = "";
        this.answer4 = "";
        this.releaseDate = "";
        this.img_path = "";
    };

    /*
    public Card(String question, String answer1, String answer2, String answer3, String answer4, String releaseDate,String category_id)  {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.releaseDate = releaseDate;
        this.category_id = category_id;
    }
    */

    public Card(int cardId, String question, String answer1, String answer2, String answer3, String answer4, String releaseDate)  {
        this.id = id;
        this.cardId = cardId;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.releaseDate = releaseDate;
        this.img_path = img_path;
    }

    public Card(int cardId, String question, String answer1, String answer2, String answer3, String answer4, String releaseDate, String category, String subject)  {
        this.id = id;
        this.cardId = cardId;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.releaseDate = releaseDate;
        this.img_path = img_path;
        this.category = category;
        this.subject = subject;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardId()  { return cardId;}

    public void setCardId(int cardId)  { this.cardId = cardId; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImg_path()  {return img_path;}

    public void setImg_path(String img_path)  {this.img_path = img_path;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
