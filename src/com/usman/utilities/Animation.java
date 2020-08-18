package com.usman.utilities;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;


public class Animation {

    public static final Interpolator EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    //=================================================NAV BUTTON ANIMATION =================================================================

    public static void addButtonAns(Rectangle fillInButton) {
        KeyValue width = new KeyValue(fillInButton.widthProperty(), 176, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2), width);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }


    public static void removeButtonAns(Rectangle fillInBtton) {
        KeyValue width = new KeyValue(fillInBtton.widthProperty(), fillInBtton.getWidth() - 176, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2), width);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }



    public static void bounceInLeft(Node node, double setFromX, double delay) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(.3), node);
        t.setFromX(-setFromX);
        t.setToX(10);
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(.2), node);
        t1.setFromX(10);
        t1.setToX(-10);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(.2), node);
        t2.setFromX(-10);
        t2.setToX(0);
        SequentialTransition sequentialTransition = new SequentialTransition(t, t1, t2);
        sequentialTransition.setDelay(Duration.seconds(delay));
        sequentialTransition.play();
    }

    // Search Animation
    public static void searchBtnAnimation(TextField textField, double width) {
        KeyValue keyValue = new KeyValue(textField.prefWidthProperty(), textField.getWidth() - width, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }


    // Animation For Error Message On TextField and etc............

    public static SequentialTransition bounceOutUp(Node node,double setFromY,double delay,double[] duration){

        node.setVisible(true);

        TranslateTransition t = new TranslateTransition(Duration.seconds(duration[0]), node);
        t.setFromY(0);
        t.setToY(-6);

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(duration[1]), node);
        t1.setFromY(-6);
        t1.setToY(6);

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(duration[2]), node);
        t2.setFromY(6);
        t2.setToY(-setFromY);


        FadeTransition ft = new FadeTransition(Duration.seconds(duration[3]), node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setDelay(Duration.seconds(duration[4]));
        ft.play();
        SequentialTransition sequentialTransition = new SequentialTransition(t, t1, t2);
        sequentialTransition.setDelay(Duration.seconds(delay));
        sequentialTransition.play();
        return sequentialTransition;

    }



    public static void bounceInRight(Node node, double setFromX, double delay) {
        TranslateTransition t = new TranslateTransition(Duration.millis(100), node);
        t.setFromX(setFromX);
        t.setToX(-10);
        TranslateTransition t1 = new TranslateTransition(Duration.millis(90), node);
        t1.setFromX(-10);
        t1.setToX(10);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(90), node);
        t2.setFromX(10);
        t2.setToX(0);
        SequentialTransition sequentialTransition = new SequentialTransition(t, t1, t2);
        sequentialTransition.setDelay(Duration.seconds(delay));
        sequentialTransition.play();
    }


    public static void bounceInDown(Node node, double setFromY, double delay) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(.2), node);
        t.setFromY(-setFromY);
        t.setToY(10);
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(.1), node);
        t1.setFromY(10);
        t1.setToY(-6);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(.1), node);
        t2.setFromY(-6);
        t2.setToY(0);
        SequentialTransition sequentialTransition = new SequentialTransition(t, t1, t2);
        sequentialTransition.setDelay(Duration.seconds(delay));
        sequentialTransition.play();
    }

    public static void bounceInUp(Node node, double setFromY, double delay) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(.3), node);
        t.setFromY(setFromY);
        t.setToY(-10);
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(.2), node);
        t1.setFromY(-10);
        t1.setToY(6);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(.2), node);
        t2.setFromY(6);
        t2.setToY(0);
        SequentialTransition sequentialTransition = new SequentialTransition(t, t1, t2);
        sequentialTransition.setDelay(Duration.seconds(delay));
        sequentialTransition.play();
    }

    public static FadeTransition fade(Node node, double delay,double from,double to) {
        FadeTransition ft = new FadeTransition(Duration.seconds(.6), node);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.setDelay(Duration.seconds(delay));
        ft.play();
        return ft;
    }

    public static void fadeInDown(Node node, double xValue, double delay) {
        node.setVisible(true);
        fade(node, delay,0,1);
        bounceInDown(node, xValue, delay);
    }

    public static void fadeInUp(Node node, double xValue, double delay) {
        node.setVisible(true);
        fade(node, delay,0,1);
        bounceInUp(node, xValue, delay);
    }

    public static void fadeInLeft(Node node, double xValue, double delay) {

        fade(node, delay,0,1);
        bounceInLeft(node, xValue, delay);
    }

    public static void fadeInRight(Node node, double xValue, double delay) {
        fade(node, delay,0,1);
        bounceInRight(node, xValue, delay);
    }




    public static void pulse(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.4), node);
        scaleTransition.setByX(1.001);
        scaleTransition.setByY(1.001);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.setDelay(Duration.seconds(.3));
        scaleTransition.play();
    }





    public static <T> ScaleTransition usmanScaleAns(T tObject, double x, double y, double duration, boolean reverse, int time){

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), (Node) tObject);
        scaleTransition.setByY(y);
        scaleTransition.setByX(x);
        scaleTransition.setAutoReverse(reverse);
        scaleTransition.setCycleCount(time);
        scaleTransition.setInterpolator(Interpolator.EASE_IN);
        scaleTransition.play();
        return scaleTransition;

    }

    public static <T> RotateTransition usmanRotationAns(T ansObject, double angle, boolean reverse, int duration, double time){

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(time),(Node)ansObject);
        rotateTransition.setByAngle(angle);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setCycleCount(duration);
        rotateTransition.play();
        return  rotateTransition;

    }

    public  static <T>FadeTransition usmanFadeAns(T ansObject, double from, double to, double duration, boolean reverse, int time){
        FadeTransition fadeTransition =new FadeTransition(Duration.seconds(duration),(Node)ansObject);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setCycleCount(time);
        fadeTransition.setAutoReverse(reverse);
        fadeTransition.play();
        return fadeTransition;
    }

    public  static <T> TranslateTransition usmanTraslateAns(T ansObject, double x, double y, double duration, boolean reverse, int time,double delay){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration),(Node)ansObject);
        translateTransition.setByX(x);
        translateTransition.setByY(y);
        translateTransition.setCycleCount(time);
        translateTransition.setAutoReverse(reverse);
        translateTransition.setDelay(Duration.seconds(delay));
        translateTransition.play();
        return translateTransition;
    }

    public static javafx.animation.Animation usmanTypingAns(String content, Text node, double duration, boolean reverse, int time){


        final javafx.animation.Animation animation = new Transition() {
            {
                setCycleDuration(Duration.seconds(duration));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                node.setText(content.substring(0, n));

            }

        };

        animation.setAutoReverse(reverse);
        animation.setCycleCount(time);
        animation.play();
        return  animation;
    }




    //======================================== Pop Animation ====================================================================

    public  static void popAns(Node ansObject, Button button, Button button1){


        button.setDisable(true);

        Animation.usmanScaleAns(ansObject, .2, .2, .1, false, 0).setOnFinished(
                event -> Animation.usmanScaleAns(ansObject, -.2, -.2, .1, false, 0).setOnFinished(
                        event1 -> Animation.usmanScaleAns(ansObject, .2, .2, .1, true, 2).setOnFinished(
                                e->{
                                    button.setDisable(false);
                                    button1.setDisable(false);
                                }
                        )
                )
        );

    }

    public  static void popAns(Node ansObject){
        Toolkit.getDefaultToolkit().beep();
        Animation.usmanScaleAns(ansObject, .2, .2, .1, false, 0).setOnFinished(
                event -> Animation.usmanScaleAns(ansObject, -.2, -.2, .1, false, 0).setOnFinished(
                        event1 -> Animation.usmanScaleAns(ansObject, .2, .2, .1, true, 2)
                )
        );
    }




    //==================================================== DASHBOARD BUTTON ANIMATION ========================================================

    public static void setDashboardButtonAns(Rectangle fillInButton, JFXButton ansBtn, double widthFill){
        ansBtn.setPrefWidth(widthFill+25);
        KeyValue width = new KeyValue(fillInButton.widthProperty(),widthFill,Interpolator.EASE_IN);
        KeyValue op = new KeyValue(ansBtn.opacityProperty(),.1,Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2),width,op);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }


    public static void removeDashboardButtonAns(Rectangle fillInButton, JFXButton ansBtn,double widthFill){
        ansBtn.setPrefWidth(widthFill+25);
        KeyValue width = new KeyValue(fillInButton.widthProperty(),fillInButton.getWidth()-widthFill,Interpolator.EASE_IN);
        KeyValue op = new KeyValue(ansBtn.opacityProperty(),1,Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2),width,op);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

    }


    //=============================================== STUDENT HEADER ANIMATION ==================================================

    public static void  studentAns(HBox studentNavPane, Pane studentHeadingPane){
        studentHeadingPane.setTranslateX(1300);
        Animation.usmanTraslateAns(studentHeadingPane,-1300,0,1,false,1,0);
        Animation.usmanRotationAns(studentHeadingPane,16,false,1,.3).setOnFinished(event ->
                Animation.usmanRotationAns(studentHeadingPane,-32,true,2,.3).setOnFinished(event1 -> {
                    Animation.usmanRotationAns(studentHeadingPane,-16,false,1,.3).setOnFinished(event2 -> {
                        Animation.usmanTraslateAns(studentNavPane,-1300,0,.6,false,1,0);
                    });
                })
        );

    }



    //=============================================== BLUR EFFECT ANIMATION ========================================================


    public static void blurEffect(Node node,Double blurValue){

        GaussianBlur blur = new GaussianBlur(0);
        node.setEffect(blur);
        DoubleProperty value = new SimpleDoubleProperty(0);
        value.addListener((observable, oldV, newV)->
        {
            blur.setRadius(newV.doubleValue());
        });

        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(value, blurValue);
        final KeyFrame kf = new KeyFrame(Duration.seconds(.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public static void zoomIn(Node node){

        Timeline t= new Timeline(

                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.opacityProperty(), 0, Interpolator.EASE_IN),
                        new KeyValue(node.scaleXProperty(), 0.3, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 0.3, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 0.3, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(node.opacityProperty(), 1., Interpolator.EASE_IN),
                        new KeyValue(node.scaleXProperty(), 1.2, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 1.2, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1.2, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_OUT)
                )

        );
        t.play();
        t.setOnFinished(event ->
                skewAns(node)
        );

    }


    public static void skewAns(Node node){

        new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(node.scaleXProperty(), 1.25, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 0.75, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.scaleXProperty(), 0.75, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 1.25, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1,Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(node.scaleXProperty(), 1.15, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 0.85, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(650),
                        new KeyValue(node.scaleXProperty(), 0.95, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 1.05, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(750),
                        new KeyValue(node.scaleXProperty(), 1.05, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 0.95, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_IN),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_IN),
                        new KeyValue(node.scaleZProperty(), 1, Interpolator.EASE_IN)
                )
        ).play();
    }


    public static void delay(double sec, CallBack callBack){

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(sec)));
        timeline.play();
        timeline.setOnFinished(e->callBack.execute());

    }


    public static void zoomOutLeft(Node an,Node node , CallBack callBack){

        Animation.usmanFadeAns(an,1,0,1,true,2);

        Timeline timeline =new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.translateXProperty(), 0, EASE),
                        new KeyValue(node.opacityProperty(), 1, EASE),
                        new KeyValue(node.scaleXProperty(), 1, EASE),
                        new KeyValue(node.scaleYProperty(), 1,EASE),
                        new KeyValue(node.scaleZProperty(), 1,EASE)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.opacityProperty(), 1, EASE),
                        new KeyValue(node.translateXProperty(), -42, EASE),
                        new KeyValue(node.scaleXProperty(), 0.475, EASE),
                        new KeyValue(node.scaleYProperty(), 0.475, EASE),
                        new KeyValue(node.scaleZProperty(), 0.475, EASE)
                ),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(node.translateXProperty(), 2000, EASE),
                        new KeyValue(node.opacityProperty(), 0, EASE),
                        new KeyValue(node.scaleXProperty(), 0.1,EASE),
                        new KeyValue(node.scaleYProperty(), 0.1,EASE),
                        new KeyValue(node.scaleZProperty(), 0.1,EASE)
                ));

        timeline.play();
        timeline.setOnFinished( event -> callBack.execute());

    }

    public static void zoomOutLeft(Node node , CallBack callBack){

        Animation.usmanFadeAns(node,1,0,1,true,2);

        Timeline timeline =new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.translateXProperty(), 0, EASE),
                        new KeyValue(node.opacityProperty(), 1, EASE),
                        new KeyValue(node.scaleXProperty(), 1, EASE),
                        new KeyValue(node.scaleYProperty(), 1,EASE),
                        new KeyValue(node.scaleZProperty(), 1,EASE)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.opacityProperty(), 1, EASE),
                        new KeyValue(node.translateXProperty(), -42, EASE),
                        new KeyValue(node.scaleXProperty(), 0.475, EASE),
                        new KeyValue(node.scaleYProperty(), 0.475, EASE),
                        new KeyValue(node.scaleZProperty(), 0.475, EASE)
                ),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(node.translateXProperty(), 2000, EASE),
                        new KeyValue(node.opacityProperty(), 0, EASE),
                        new KeyValue(node.scaleXProperty(), 0.1,EASE),
                        new KeyValue(node.scaleYProperty(), 0.1,EASE),
                        new KeyValue(node.scaleZProperty(), 0.1,EASE)
                ));

        timeline.play();
        timeline.setOnFinished( event -> callBack.execute());

    }


}

