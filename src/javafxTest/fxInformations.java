package javafxTest;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by romainhry on 07/01/2017.
 */
public class fxInformations extends Group {

    private Text nbTunnel;
    private Text nbTrain;
    private Text nbWagon;
    private Text nbLine;

    private ImageView imageTunnel;
    private ImageView imageTrain;
    private ImageView imageWagon;
    private ImageView imageLine;

    private Text textInfo;


    public fxInformations(double posX, double posY){
        super();

        Image i1 = new Image("file:src/img/tunnel.png",40,40,false,false);
        Image i2 = new Image("file:src/img/train.png",40,40,false,false);
        Image i3 = new Image("file:src/img/wagon.png",40,40,false,false);
        Image i4 = new Image("file:src/img/rails.png",40,40,false,false);

        imageTunnel = new ImageView(i1);
        imageTrain = new ImageView(i2);
        imageWagon = new ImageView(i3);
        imageLine = new ImageView(i4);


        imageTrain.setX(posX);
        imageTrain.setY(posY);
        nbTrain = new Text(posX+40, posY+5,"3");
        nbTrain.setFill(Color.BISQUE);
        nbTrain.setFont(Font.font(null, FontWeight.BOLD,15));

        imageWagon.setX(posX+65);
        imageWagon.setY(posY+4);
        nbWagon = new Text(posX+105, posY+5,"0");
        nbWagon.setFill(Color.BISQUE);
        nbWagon.setFont(Font.font(null, FontWeight.BOLD,15));

        imageLine.setX(posX+130);
        imageLine.setY(posY+6);
        nbLine = new Text(posX+170, posY+5,"3");
        nbLine.setFill(Color.BISQUE);
        nbLine.setFont(Font.font(null, FontWeight.BOLD,15));

        imageTunnel.setX(posX+195);
        imageTunnel.setY(posY);
        nbTunnel = new Text(posX+235, posY+5,"3");
        nbTunnel.setFill(Color.BISQUE);
        nbTunnel.setFont(Font.font(null, FontWeight.BOLD,15));

        textInfo = new Text(400, 250,"");
        textInfo.setFill(Color.BISQUE);
        textInfo.setFont(Font.font(null, FontWeight.BOLD,25));


        getChildren().add(textInfo);

        getChildren().add(nbLine);
        getChildren().add(nbTrain);
        getChildren().add(nbTunnel);
        getChildren().add(nbWagon);

        getChildren().add(imageLine);
        getChildren().add(imageTrain);
        getChildren().add(imageTunnel);
        getChildren().add(imageWagon);

    }

    public void setNbTunnel(int i)
    {
        nbTunnel.setText(Integer.toString(i));
    }

    public void setNbTrain(int i)
    {
        nbTrain.setText(Integer.toString(i));
    }

    public void setNbWagon(int i)
    {
        nbWagon.setText(Integer.toString(i));
    }

    public void setNbLine(int i)
    {
        nbLine.setText(Integer.toString(i));
    }


    public void setTextInfo(String s)
    {
        textInfo.setText(s);
    }




}
