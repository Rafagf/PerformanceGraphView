package com.rafag.performancegraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Garcia on 03/09/15.
 */
public class MyGraphView extends View {

    //View drawing parameters
    int viewHeightPx;
    int viewWidthPx;
    int viewHeightDp;
    int viewWidthDp;
    int cirCenterX;
    int cirCenterY;
    int cirRadio;
    int skillCoordinatesX [];
    int skillCoordinatesY [];
    //Distance from the border of the circumference to skills information
    int distanceFromCirToSkillInfo;
    //Performance graphs
    PerformanceGraph newPerformanceGraph;
    PerformanceGraph oldPerformanceGraph;
    //Variables to paint in our canvas
    private Paint drawPaint;
    private Path path = new Path();

    public MyGraphView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setupPaint();
        setSkills();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        setupGraph(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Drawing circumference
        drawCircumference(canvas);
        //Drawing lines inside the circumference (pie chart)
        drawSkillsLines(canvas);
        //Drawing skills information (skill circle, name and score)
        drawSkillsInformation(canvas);
        //Draw old performance graph
        drawPerformancePolygon(canvas, oldPerformanceGraph);
        //Drawing new performance graph
        drawPerformancePolygon(canvas, newPerformanceGraph);
    }

    /**
     * It sets a set of skills
     */
    private void setSkills() {

        List<Skill> skillList = new ArrayList<>();

        Skill skill1 = new Skill(512, 70, "Attack", R.color.purple);
        Skill skill2 = new Skill(600, 90, "Defense", R.color.green);
        Skill skill3 = new Skill(800, 90, "Speed", R.color.orange);
        Skill skill4 = new Skill(876, 70, "Agility", R.color.custom_blue);
        Skill skill5 = new Skill(700, 90, "Pace", R.color.pink);

        skillList.add(skill1); skillList.add(skill2); skillList.add(skill3);
        skillList.add(skill4); skillList.add(skill5);

        //Creating new performance graph with skills and color (light gray with transparency)
        newPerformanceGraph = new PerformanceGraph(skillList, Color.argb(200, 214, 214, 214));

        List<Skill> skillList2 = new ArrayList<>();

        Skill skill21 = new Skill(512, 100, "Attack", R.color.purple);
        Skill skill22 = new Skill(600, 100, "Defense", R.color.green);
        Skill skill23 = new Skill(800, 80, "Speed", R.color.orange);
        Skill skill24 = new Skill(876, 77, "Agility", R.color.custom_blue);
        Skill skill25 = new Skill(700, 97, "Pace", R.color.pink);

        skillList2.add(skill21); skillList2.add(skill22); skillList2.add(skill23);
        skillList2.add(skill24); skillList2.add(skill25);

        //Creating new performance graph with skills and color (custom blue with transparency)
        oldPerformanceGraph = new PerformanceGraph(skillList2, Color.argb(120, 0, 184, 252));

//        Brain map
//        List<Skill> skillList = new ArrayList<>();
//
//        Skill skill1 = new Skill(512, 70, "Language", R.color.purple);
//        Skill skill2 = new Skill(600, 20, "Problem Solving", R.color.green);
//        Skill skill3 = new Skill(800, 90, "Memory", R.color.orange);
//        Skill skill4 = new Skill(876, 60, "Skill X", R.color.custom_blue);
//        Skill skill5 = new Skill(700, 90, "Focus", R.color.pink);
//        Skill skill6 = new Skill(924, 90, "Mental agility", R.color.blue);
//        Skill skill7 = new Skill(200, 42, "Skill Y", R.color.red);
//
//        skillList.add(skill1); skillList.add(skill2); skillList.add(skill3);
//        skillList.add(skill4); skillList.add(skill5); skillList.add(skill6);
//        skillList.add(skill7);
//
//        //Creating new performance graph with skills and color (light gray with transparency)
//        newPerformanceGraph = new PerformanceGraph(skillList, Color.argb(200, 214, 214, 214));
//
//        List<Skill> skillList2 = new ArrayList<>();
//
//        Skill skill21 = new Skill(512, 90, "Language", R.color.purple);
//        Skill skill22 = new Skill(600, 100, "Problem Solving", R.color.green);
//        Skill skill23 = new Skill(800, 90, "Memory", R.color.orange);
//        Skill skill24 = new Skill(876, 80, "Skill X", R.color.custom_blue);
//        Skill skill25 = new Skill(700, 100, "Focus", R.color.pink);
//        Skill skill26 = new Skill(924, 70, "Mental agility", R.color.blue);
//        Skill skill27 = new Skill(200, 80, "Skill Y", R.color.red);
//
//        skillList2.add(skill21); skillList2.add(skill22); skillList2.add(skill23);
//        skillList2.add(skill24); skillList2.add(skill25); skillList2.add(skill26);
//        skillList2.add(skill27);
//
//        //Creating new performance graph with skills and color (custom blue with transparency)
//        oldPerformanceGraph = new PerformanceGraph(skillList2, Color.argb(120, 0, 184, 252));
    }

    /**
     * Paint setup
     */
    private void setupPaint() {

        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * Graph setup. General parameters such as view width/height or skills coordinates in graph
     * @param context context
     */
    public void setupGraph(Context context){

        //Getting view's measures and setting some general parameters
        viewWidthPx = getWidth();
        viewHeightPx = getHeight();
        viewHeightDp = (int)DeviceDimensionsHelper.convertPixelsToDp(viewHeightPx, context);
        viewWidthDp = (int)DeviceDimensionsHelper.convertPixelsToDp(viewWidthPx, context);
        distanceFromCirToSkillInfo = (int)DeviceDimensionsHelper.convertDpToPixel(15, getContext());

        //Circumference (graph) center
        cirCenterX = (int)DeviceDimensionsHelper.convertDpToPixel(viewWidthDp/2, getContext());
        cirCenterY = (int)DeviceDimensionsHelper.convertDpToPixel(viewHeightDp/2, getContext());

        /* We give the graph half of the view size minus some extra space for the skills info. Also,
        To decide the radio of the circumference, we check which is smaller: width or height view */
        if(viewWidthDp <= viewHeightDp) {
            cirRadio = (int) DeviceDimensionsHelper.convertDpToPixel(
                    viewWidthDp / 2 - distanceFromCirToSkillInfo*2, getContext());
        }
        else{
            cirRadio = (int) DeviceDimensionsHelper.convertDpToPixel(
                    viewHeightDp / 2 - distanceFromCirToSkillInfo*2, getContext());
        }

        //Setting skills coordinates for every available skill
        setSkillCoordinates(newPerformanceGraph.getSkillList().size());
    }

    /**
     * It sets skills coordinates inside the view
     * @param portions number of skills (pie chart portions)
     */
    public void setSkillCoordinates(int portions){

        skillCoordinatesX = new int[portions];
        skillCoordinatesY = new int[portions];

        int angle = 0;

        //Using trigonometry we can calculate the coordinates of each skill in the graph
        for(int i = 0; i < portions; i++){

            angle = i * (360/portions);
            skillCoordinatesX[i] = (int) (cirCenterX + cirRadio * Math.sin(Math.toRadians(angle)));
            skillCoordinatesY[i] = (int) (cirCenterY + cirRadio * Math.cos(Math.toRadians(angle)));
        }
    }

    /**
     * It draws the circumference where our performance polygon is drawn
     * @param canvas the canvas where to write
     */
    public void drawCircumference(Canvas canvas){

        path.reset();
        int radioDistance = cirRadio/10;
        drawPaint.setStyle(Paint.Style.STROKE);

        //We draw our circumference and 9 other circumferences inside
        for(int i = 0; i < 10; i++){

            if(i%2 == 0){
                drawPaint.setColor(getResources().getColor(R.color.light_grey));
            }
            else{
                drawPaint.setColor(getResources().getColor(R.color.grey));
            }

            canvas.drawCircle(cirCenterX, cirCenterY, cirRadio - radioDistance*i, drawPaint);
        }
    }

    /**
     * It draws the skill lines (pie chart lines)
     * @param canvas the canvas where to write
     */
    public void drawSkillsLines(Canvas canvas){

        path.reset();
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setColor(getResources().getColor(R.color.light_grey));
        path.moveTo(cirCenterX, cirCenterY);

        for(int i = 0; i < newPerformanceGraph.getSkillList().size(); i++){

            path.lineTo(skillCoordinatesX[i], skillCoordinatesY[i]);
            path.moveTo(cirCenterX, cirCenterY);
        }

        canvas.drawPath(path, drawPaint);
    }

    /**
     * It draws the performance polygon
     * @param canvas the canvas where to write
     */
    public void drawPerformancePolygon(Canvas canvas, PerformanceGraph performanceGraph){

        //List of skills for the performance graph
        List<Skill> skillList = performanceGraph.getSkillList();

        if(skillList.size() > 0) {

            path.reset();
            drawPaint.setStyle(Paint.Style.FILL);

            //Light gray with transparency
            drawPaint.setColor(performanceGraph.getColor());

            path.moveTo(cirCenterX, cirCenterY);
            Point firstPoint = getCoordinatesInsideCircumference(0, skillList.get(0).getGraphScore());
            path.lineTo(firstPoint.x, firstPoint.y);

            for (int i = 1; i < skillList.size(); i++) {

                Point point = getCoordinatesInsideCircumference(i, skillList.get(i).getGraphScore());
                path.lineTo(point.x, point.y);
            }

            //We come back to the first one to close the graph
            path.lineTo(firstPoint.x, firstPoint.y);
            path.moveTo(cirCenterX, cirCenterY);
            canvas.drawPath(path, drawPaint);
        }
    }

    /**
     * It gets the coordinates for a given skill and a score inside the view
     * @param portion the skill number (portion pie chart). 0 = bottom point and then counterclockwise
     * @param score the score achieved for that skill (range: 0-100)
     */
    public Point getCoordinatesInsideCircumference(int portion, int score){

        Point point = new Point();

        //We need to normalize the radio size to the highest score (100)
        float radioNormalized = ((float)cirRadio)/100;
        float skillRadio = radioNormalized*score;

        //Applying trigonometry to get the point
        int angle = portion * (360/newPerformanceGraph.getSkillList().size());
        int x = (int) (cirCenterX + skillRadio * Math.sin(Math.toRadians(angle)));
        int y = (int) (cirCenterY + skillRadio * Math.cos(Math.toRadians(angle)));

        point.set(x, y);

        return point;
    }

    /**
     * It gets the coordinates for a given skill circle
     * @param portion the skill number (portion pie chart). 0 = bottom point and then counterclockwise
     * @param distance the distance from the circumference border to the circle to be painted
     */
    public Point getCoordinatesInViewForSkillsCircle(int portion, int distance){

        Point point = new Point();

        int angle = portion * (360/newPerformanceGraph.getSkillList().size());
        int x = (int) (cirCenterX + ((cirRadio + distance) * Math.sin(Math.toRadians(angle))));
        int y = (int) (cirCenterY + ((cirRadio + distance) * Math.cos(Math.toRadians(angle))));

        point.set(x, y);

        return point;
    }

    /**
     * It draws everything related to the skills: lines from the circumference,
     * coloured circle, skill name and skill score
     * @param canvas the canvas where to paint
     */
    private void drawSkillsInformation(Canvas canvas) {

        path.reset();
        int skillCircleRadio = cirRadio/20;

        //List of skills for the performance graph
        List<Skill> skillList = newPerformanceGraph.getSkillList();

        for(int i = 0; i < newPerformanceGraph.getSkillList().size(); i++) {

            drawPaint.setStyle(Paint.Style.STROKE);

            //Getting coordinates for the end of the line that leads to the skill circle
            Point linePoint = getCoordinatesInViewForSkillsCircle(i, distanceFromCirToSkillInfo);
            //Getting coordinates for skill small circle
            Point circlePoint = getCoordinatesInViewForSkillsCircle(i, distanceFromCirToSkillInfo + skillCircleRadio);

            //Drawing line from the border of the circumference to the calculated position
            path.moveTo(skillCoordinatesX[i], skillCoordinatesY[i]);
            drawPaint.setColor(getResources().getColor(R.color.light_grey));
            path.lineTo(linePoint.x, linePoint.y);
            canvas.drawPath(path, drawPaint);

            //Drawing skill circle
            drawPaint.setColor(getResources().getColor(skillList.get(i).getColor()));
            canvas.drawCircle(circlePoint.x, circlePoint.y, skillCircleRadio, drawPaint);

            //Drawing skill information
            drawPaint.setStyle(Paint.Style.FILL);
            drawPaint.setTextSize(22);
            drawPaint.setColor(getResources().getColor(R.color.light_grey));
            drawPaint.setTextAlign(Paint.Align.CENTER);

            if(linePoint.y > cirCenterY) {

                //Skill's name
                drawPaint.setColor(getResources().getColor(R.color.light_grey));
                canvas.drawText(skillList.get(i).getName(),
                        circlePoint.x, circlePoint.y + distanceFromCirToSkillInfo + skillCircleRadio, drawPaint);
                //Skill's points
                drawPaint.setColor(getResources().getColor(R.color.white));
                canvas.drawText(String.valueOf(skillList.get(i).getPointsScore()),
                        circlePoint.x, circlePoint.y + distanceFromCirToSkillInfo + skillCircleRadio + drawPaint.getFontSpacing(),
                        drawPaint);
            }

            else{
                //Skill's points
                drawPaint.setColor(getResources().getColor(R.color.white));
                canvas.drawText(String.valueOf(skillList.get(i).getPointsScore()),
                        circlePoint.x, circlePoint.y - distanceFromCirToSkillInfo, drawPaint);
                //Skill's name
                drawPaint.setColor(getResources().getColor(R.color.light_grey));
                canvas.drawText(skillList.get(i).getName(),
                        circlePoint.x, circlePoint.y - distanceFromCirToSkillInfo - drawPaint.getFontSpacing(), drawPaint);
            }
        }
    }
}
