package sample;
import com.sun.source.tree.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;

import static java.awt.Color.RED;

class node{
    String name;
    int xc,yc,weight;
    node(String name,int xc,int yc){
        this.name=name;
        this.xc=xc;
        this.yc=yc;
    }
}

class top{
    node obj;
    ArrayList<node> list = new ArrayList<node>();
    top(node obj){
        this.obj = obj;
    }
}

public class Controller {

    @FXML
    TextField tf1,tfx,tfy,tfs,tfd,tf4,tf5,tf6,tf7,tf8,tf9,tfe1,tfe2,tfd1,tfd2,tfm1,tfm2,tfw1,tfs1,tfs2,tfimp,type;
    @FXML
    TextArea ta1;

    @FXML
    Button b1;

    @FXML
    Button b2;
    @FXML
    Button tg;
    @FXML
    AnchorPane gfield;

    ArrayList<top> arr = new ArrayList<top>();
    int togglecount=0,count=0;
    String prv;

    public void fun(ActionEvent E){
        try{
            String s = tf1.getText();
            String sx = tfx.getText();
            String sy = tfy.getText();

            node obj1 = new node(s,Integer.parseInt(sx),Integer.parseInt(sy));
            top obj2 = new top(obj1);
            int flag=0;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s)){
                    flag=1;
                    ta1.setText("Invalid node: node already present");
                    break;
                }
                if(arr.get(i).obj.xc==Integer.parseInt(sx)&&arr.get(i).obj.yc==Integer.parseInt(sy)){
                    flag=1;
                    ta1.setText("Invalid node: another node already present at this position ");
                }
            }
            if(flag==0) {
                Circle c1 = new Circle(10*Integer.parseInt(sx),10*Integer.parseInt(sy),10);
                c1.setFill(Color.BLUE);
                gfield.getChildren().add(c1);
                Text text = new Text();
                text.setX(10*Integer.parseInt(sx));
                text.setY(10*Integer.parseInt(sy)-2);
                text.setText(s);
                text.setFill(Color.WHITESMOKE);
                gfield.getChildren().add(text);
                ta1.setText("Added succesfully");
                arr.add(obj2);
            }
            if(flag==1){
                throw new Exception();
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }


    }

    public void change(ActionEvent E){
        if(togglecount==0){
            togglecount=1;
        }
        else
            togglecount=0;
    }

    public void touch(MouseEvent E){
        if(togglecount==0)
            return;
        if(togglecount==1 && !E.isShiftDown()){
            int x1 = (int)E.getX();
            int x2 = (int)E.getY();
            x1=x1/10;
            x2=x2/10;
            TextInputDialog td1 = new TextInputDialog("Enter vertex name");
            td1.showAndWait();
            String s = td1.getEditor().getText();
            tf1.setText(s);
            tfx.setText(Integer.toString(x1));
            tfy.setText(Integer.toString(x2));
            b1.fire();
        }
        if(togglecount==1 && E.isShiftDown()){
            int flag=0;
            count++;
            int x1 = (int)E.getX();
            int y1 = (int)E.getY();
            x1=x1/10;
            y1=y1/10;
            String s3="";
            for(int i=0;i<arr.size();i++){
                if(Math.abs(x1-arr.get(i).obj.xc)<=1 && Math.abs(y1-arr.get(i).obj.yc)<=1){
                    flag=1;
                    if(count%2==1){
                        prv= arr.get(i).obj.name;
                        break;
                    }
                    else{
                        tf7.setText(prv);
                        tf8.setText(arr.get(i).obj.name);
                        TextInputDialog td1 = new TextInputDialog("Enter edge weight");
                        td1.showAndWait();
                        tf9.setText(td1.getEditor().getText());
                        b2.fire();
                        break;
                    }
                }
            }
            if(flag==0){
                count--;
            }

        }


    }

    public void search(ActionEvent E){
        try{
            String s = tfs.getText();
            int flag=0,x=0,y=0;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s)){
                    flag=1;
                    x=arr.get(i).obj.xc;
                    y=arr.get(i).obj.yc;
                    break;
                }
            }
            if(flag==1){
                ta1.setText("vertex found at "+x+","+y);
            }
            else{

                ta1.setText("Vertex not found");
                throw new Exception();
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void del(ActionEvent E){
        try{
            String s = tfd.getText();
            int flag=-1;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s)){
                    flag=i;
                    break;
                }
            }

            if(flag!=-1){
                arr.remove(flag);
                for(int i=0;i<arr.size();i++){
                    for(int j=0;j<arr.get(i).list.size();j++){
                        if(arr.get(i).list.get(j).name.equals(s)){
                            arr.get(i).list.remove(j);
                            break;
                        }
                    }
                }
                ta1.setText("Vertex removed successfully");
                again();
            }
            else{

                ta1.setText("Vertex not found");
                throw new Exception();
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void again(){
        gfield.getChildren().clear();
        for(int i=0;i<arr.size();i++){
            String s = arr.get(i).obj.name;
            int x,y;
            x = arr.get(i).obj.xc;
            y = arr.get(i).obj.yc;
            Circle c1 = new Circle(10*x,10*y,10);
            c1.setFill(Color.BLUE);
            gfield.getChildren().addAll(c1);
            Text text = new Text();
            text.setX(10*x);
            text.setY(10*y-2);
            text.setText(s);
            text.setFill(Color.WHITE);
            gfield.getChildren().add(text);
        }
        for(int i=0;i<arr.size();i++){
            int x1=arr.get(i).obj.xc;
            int y1 = arr.get(i).obj.yc;
            for(int j=0;j<arr.get(i).list.size();j++){
                int x2 = arr.get(i).list.get(j).xc;
                int y2 = arr.get(i).list.get(j).yc;
                Line l1 = new Line();
                l1.setStartX(x1*10);
                l1.setStartY(y1*10);
                l1.setEndX(x2*10);
                l1.setEndY(y2*10);
                l1.setStroke(Color.BLUE);
                gfield.getChildren().add(l1);
            }
        }
    }

    public void fun1(ActionEvent E){
        try{
            String x1 = tf4.getText();
            String x3 = tf5.getText();
            String x4 = tf6.getText();
            int flag=0,ind=-1;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(x1)){
                    ind=i;
                    break;
                }
            }
            if(ind == -1){

                ta1.setText("cannot modify: vertex doesn't exist");
                throw new Exception();
            }
            else {
                arr.get(ind).obj.xc = Integer.parseInt(x3);
                arr.get(ind).obj.yc = Integer.parseInt(x4);
                ta1.setText("vertex modified successfully");
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void fun2(ActionEvent E){
        try{
            String s1,s2,s3;
            s1 = tf7.getText();
            s2 = tf8.getText();
            s3 = tf9.getText();
            int ind1=-1,ind2=-1;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s1)){
                    ind1 = i;
                    break;
                }
            }
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s2)){
                    ind2 = i;
                    break;
                }
            }
            if(ind1==-1||ind2==-1){

                ta1.setText("Invalid input");
                throw new Exception();
            }
            else{
                //node n1 = new node(arr.get(ind1).obj.name,arr.get(ind1).obj.xc,arr.get(ind1).obj.yc);
                node n2 = new node(arr.get(ind2).obj.name,arr.get(ind2).obj.xc,arr.get(ind2).obj.yc);
                //n1.weight=Integer.parseInt(s3);
                n2.weight=Integer.parseInt(s3);
                Line l1 = new Line();
                l1.setStartX(arr.get(ind1).obj.xc*10);
                l1.setStartY(arr.get(ind1).obj.yc*10);
                l1.setEndX(n2.xc*10);
                l1.setEndY(n2.yc*10);
                l1.setStroke(Color.BLUE);
                arr.get(ind1).list.add(n2);
                //arr.get(ind2).list.add(n1);
                gfield.getChildren().add(l1);
                ta1.setText("edge added successfully ");
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void searchedge(ActionEvent E){
        try{
            String s1,s2;
            int flag=0,wt=-1;
            s1 = tfe1.getText();
            s2 = tfe2.getText();
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s1)){
                    for(int j=0;j<arr.get(i).list.size();j++){
                        if(arr.get(i).list.get(j).name.equals(s2)){
                            flag=1;
                            wt=arr.get(i).list.get(j).weight;
                            break;
                        }
                    }
                    break;
                }
            }
            if(flag==1){
                ta1.setText("Edge found with weight "+wt);
            }
            else{

                ta1.setText("Edge not found");
                throw new Exception();
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void deleteedge(ActionEvent E){
        try{
            String s1 = tfd1.getText();
            String s2 = tfd2.getText();
            int flag=0;
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s1)){
                    for(int j=0;j<arr.get(i).list.size();j++){
                        if(arr.get(i).list.get(j).name.equals(s2)){
                            arr.get(i).list.remove(j);
                            flag=1;
                            break;
                        }
                    }
                }
//            if(arr.get(i).obj.name.equals(s2)){
//                for(int j=0;j<arr.get(i).list.size();j++){
//                    if(arr.get(i).list.get(j).name.equals(s1)){
//                        arr.get(i).list.remove(j);
//                        flag=1;
//                        break;
//                    }
//                }
//            }
            }
            if(flag==0){

                ta1.setText("Edge not found");
                throw new Exception();
            }
            else{
                again();
                ta1.setText("Edge removed successfully");
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void Modifyedge(ActionEvent E){
        try{
            String s1,s2,s3;
            int flag=0,wt=-1;
            s1 = tfm1.getText();
            s2 = tfm2.getText();
            s3 = tfw1.getText();
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).obj.name.equals(s1)){
                    for(int j=0;j<arr.get(i).list.size();j++){
                        if(arr.get(i).list.get(j).name.equals(s2)){
                            flag=1;
                            arr.get(i).list.get(j).weight = Integer.parseInt(s3);
                            break;
                        }
                    }

                }
//            if(arr.get(i).obj.name.equals(s2)){
//                for(int j=0;j<arr.get(i).list.size();j++){
//                    if(arr.get(i).list.get(j).name.equals(s1)){
//                        flag=1;
//                        arr.get(i).list.get(j).weight = Integer.parseInt(s3);
//                        break;
//                    }
//                }
//
//            }
            }
            if(flag==1){
                ta1.setText("Edge modified ");
            }
            else{

                ta1.setText("Edge not found");
                throw new Exception();
            }
        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }

    }

    public void Dijsktra(ActionEvent E){
        try{
            String s1 = tfs1.getText();
            String s2 = tfs2.getText();
            int flag=0,wt=-1;

            int[] dist = new int[100005];
            int[] par = new int[100005];
            int[] sptset = new int[100005];
            for(int i=0;i<10000;i++){
                dist[i]=10000;
                par[i]=-1;
                sptset[i]=0;
            }
            HashMap<String,Integer> hash = new HashMap<>();
            for(int i=0;i<arr.size();i++){
                hash.put(arr.get(i).obj.name,i);
            }
            int src = hash.get(s1);
            dist[src]=0;
            for(int i=0;i<arr.size();i++){
                int u = 0,mnval=10000,minidx=hash.get(s1);
                for(int j=0;j<arr.size();j++){
                    if(sptset[j]==0 && dist[j]<mnval){
                        mnval = dist[j];
                        minidx = j;
                    }
                }
                u = minidx;
                sptset[u]=1;
                for(int j=0;j<arr.get(u).list.size();j++){
                    int x= hash.get(arr.get(u).list.get(j).name);
                    if(sptset[x]==0 && dist[u]+arr.get(u).list.get(j).weight<dist[x]){
                        dist[x]=dist[u]+arr.get(u).list.get(j).weight;
                        par[x]=u;
                    }
                }
            }
            int dest = hash.get(s2);

            if(dist[dest]==10000){

                ta1.setText("No path Exists");
                throw new Exception();
            }
            String ans="";
            //again();
            ArrayList<node> dp = new ArrayList<node>();
            dp.add(arr.get(dest).obj);
            while(dest!=src && par[dest]!=-1){
                int x1=arr.get(dest).obj.xc;
                int y1= arr.get(dest).obj.yc;
                int x2=arr.get(par[dest]).obj.xc;
                int y2= arr.get(par[dest]).obj.yc;
                dp.add(arr.get(par[dest]).obj);
                Line l1 = new Line();
                l1.setStartX(x1*10);
                l1.setStartY(y1*10);
                l1.setEndX(x2*10);
                l1.setEndY(y2*10);
                l1.setStroke(Color.BLUE);
                l1.setStrokeWidth(5);
                gfield.getChildren().add(l1);
                //ans=ans+"->"+arr.get(dest).obj.name;
                dest=par[dest];
            }
            for(int i=dp.size()-1;i>0;i--){
                ans=ans+dp.get(i).name+"->";
            }
            ans=ans+dp.get(0).name;
            ta1.setText(ans);
            if(type.getText().equalsIgnoreCase("circle")){
                Circle c2 = new Circle(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10,10);
                gfield.getChildren().add(c2);
                c2.setFill(Color.RED);
                Path pt = new Path();
                MoveTo mv = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                pt.getElements().addAll(mv);
                for(int i=dp.size()-1;i>=0;i--){
                    LineTo l1 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    pt.getElements().addAll(l1);
                }
                PathTransition pt1 =  new PathTransition();
                pt1.setPath(pt);
                pt1.setDuration(Duration.millis(1500));
                pt1.setNode(c2);
                pt1.setCycleCount(50);
                pt1.play();
            }
            if(type.getText().equalsIgnoreCase("rectangle")){
                Rectangle c2 = new Rectangle(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10,20,20);
                gfield.getChildren().add(c2);
                c2.setFill(Color.RED);
                Path pt = new Path();
                MoveTo mv = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                pt.getElements().addAll(mv);
                for(int i=dp.size()-1;i>=0;i--){
                    LineTo l1 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    pt.getElements().addAll(l1);
                }
                PathTransition pt1 =  new PathTransition();
                pt1.setPath(pt);
                pt1.setDuration(Duration.millis(1500));
                pt1.setNode(c2);
                pt1.setCycleCount(50);
                pt1.play();
            }

            if(type.getText().equalsIgnoreCase("cross")){
                Line c2 = new Line();
                Line c3 = new Line();
                c2.setStartX(arr.get(src).obj.xc*10-10);
                c2.setStartY(arr.get(src).obj.yc*10-10);
                c3.setStartX(arr.get(src).obj.xc*10-10);
                c3.setStartY(arr.get(src).obj.yc*10+10);
                c2.setEndX(arr.get(src).obj.xc*10+10);
                c2.setEndY(arr.get(src).obj.yc*10+10);
                c3.setEndX(arr.get(src).obj.xc*10+10);
                c3.setEndY(arr.get(src).obj.yc*10-10);
                gfield.getChildren().add(c2);
                gfield.getChildren().add(c3);

                c2.setStroke(Color.RED);
                c2.setStrokeWidth(10);
                c3.setStrokeWidth(10);
                c3.setStroke(Color.RED);
                Path pt = new Path();
                Path pt2 = new Path();
                MoveTo mv = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                MoveTo mv1 = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                pt.getElements().addAll(mv);
                pt2.getElements().addAll(mv1);
                for(int i=dp.size()-1;i>=0;i--){
                    LineTo l1 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    LineTo l2 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    pt.getElements().addAll(l1);
                    pt2.getElements().addAll(l2);
                }
                PathTransition pt1 =  new PathTransition();
                PathTransition pt3 =  new PathTransition();
                pt1.setPath(pt);
                pt1.setDuration(Duration.millis(1500));
                pt1.setNode(c2);
                pt1.setCycleCount(50);
                pt1.play();
                pt3.setPath(pt2);
                pt3.setDuration(Duration.millis(1500));
                pt3.setNode(c3);
                pt3.setCycleCount(50);
                pt3.play();
            }
            if(type.getText().equalsIgnoreCase("plus")){
                Line c2 = new Line();
                Line c3 = new Line();
                c2.setStartX(arr.get(src).obj.xc*10);
                c2.setStartY(arr.get(src).obj.yc*10-10);
                c3.setStartX(arr.get(src).obj.xc*10-10);
                c3.setStartY(arr.get(src).obj.yc*10);
                c2.setEndX(arr.get(src).obj.xc*10);
                c2.setEndY(arr.get(src).obj.yc*10+10);
                c3.setEndX(arr.get(src).obj.xc*10+10);
                c3.setEndY(arr.get(src).obj.yc*10);
                gfield.getChildren().add(c2);
                gfield.getChildren().add(c3);

                c2.setStroke(Color.RED);
                c2.setStrokeWidth(6);
                c3.setStrokeWidth(6);
                c3.setStroke(Color.RED);
                Path pt = new Path();
                Path pt2 = new Path();
                MoveTo mv = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                MoveTo mv1 = new MoveTo(arr.get(src).obj.xc*10,arr.get(src).obj.yc*10);
                pt.getElements().addAll(mv);
                pt2.getElements().addAll(mv1);
                for(int i=dp.size()-1;i>=0;i--){
                    LineTo l1 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    LineTo l2 = new LineTo(dp.get(i).xc*10,dp.get(i).yc*10);
                    pt.getElements().addAll(l1);
                    pt2.getElements().addAll(l2);
                }
                PathTransition pt1 =  new PathTransition();
                PathTransition pt3 =  new PathTransition();
                pt1.setPath(pt);
                pt1.setDuration(Duration.millis(1500));
                pt1.setNode(c2);
                pt1.setCycleCount(50);
                pt1.play();
                pt3.setPath(pt2);
                pt3.setDuration(Duration.millis(1500));
                pt3.setNode(c3);
                pt3.setCycleCount(50);
                pt3.play();
            }

        }catch (Exception e){
            //TextInputDialog td = new TextInputDialog(e.getMessage());
            //td.showAndWait();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Error");
            al.setHeaderText("Invalid Input");
            al.setContentText("please give the input correctly");
            al.showAndWait();
        }


    }

    public void imp(ActionEvent E)throws Exception{
        String s = tfimp.getText();
        File fd = new File(s);
        try{
            Scanner sc = new Scanner(fd);
            int n = sc.nextInt();
            for(int i=0;i<n;i++){
                String nm = sc.next();
                int x = sc.nextInt();
                int y =sc.nextInt();
                tf1.setText(nm);
                tfx.setText(Integer.toString(x));
                tfy.setText(Integer.toString(y));
                b1.fire();
            }
            int n1 = sc.nextInt();
            for(int i=0;i<n1;i++){
                String frm,to,wt;
                frm = sc.next();
                to = sc.next();
                wt = sc.next();
                tf7.setText(frm);
                tf8.setText(to);
                tf9.setText(wt);
                b2.fire();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void stop(ActionEvent E){
        again();
    }

    public void wash(ActionEvent E){
        gfield.getChildren().clear();
        arr.clear();
    }
}
