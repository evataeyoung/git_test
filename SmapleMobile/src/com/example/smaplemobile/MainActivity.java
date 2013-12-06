package com.example.smaplemobile;

import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private MyView vw;
	ArrayList<Vertex> arVertex;
	ArrayList<Integer> arVerColor;
	private LinearLayout body;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vw = new MyView(this);
		body = (LinearLayout) findViewById(R.id.body);
		body.addView(vw);
		arVertex = new ArrayList<Vertex>();
		arVerColor = new ArrayList<Integer>();
	}

	public void anotherView() {

		body.removeView(vw);
		vw = new MyView(this);
		body.addView(vw);

	}

	public class Vertex {
		Vertex(float ax, float ay, boolean ad) {
			x = ax;
			y = ay;
			Draw = ad;
		}

		float x;
		float y;
		boolean Draw;
	}

	@SuppressLint("WrongCall")
	protected class MyView extends View {
		Paint mPaint;
		Canvas canvas;
		int idx = -1;

		public MyView(Context context) {
			super(context);
			// Paint 객체 미리 초기화
			mPaint = new Paint();
			mPaint.setColor(Color.BLACK);
			mPaint.setStrokeWidth(3);
			mPaint.setAntiAlias(true);
		}

		public void changeColor() {
			if (idx == -1) {
				mPaint.setColor(Color.RED);
				idx = 0;
			} else {
				mPaint.setColor(Color.BLACK);
				idx = -1;
			}

		}

		public void clearCanvas() {
			arVertex.clear();invalidate();
		}

		public void onDraw(Canvas canvase) {


			this.canvas = canvase;
			canvas.drawColor(Color.LTGRAY);
			// 정점을 순회하면서 선분으로 잇는다.
			for (int i = 0; i < arVertex.size(); i++) {
				if (arVertex.get(i).Draw) {
					if (arVerColor.get(i)==0) {
					mPaint.setColor(Color.BLACK);	
					} else{
						mPaint.setColor(Color.RED);
					}
					canvas.drawLine(arVertex.get(i - 1).x,
							arVertex.get(i - 1).y, arVertex.get(i).x,
							arVertex.get(i).y, mPaint);
					
				}
			}
		}

		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				arVertex.add(new Vertex(event.getX(), event.getY(), false));
				if (mPaint.getColor() == Color.BLACK) {
					arVerColor.add(0);	
				}else{
					arVerColor.add(1);
				}
				
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				arVertex.add(new Vertex(event.getX(), event.getY(), true));
				if (mPaint.getColor() == Color.BLACK) {
					arVerColor.add(0);	
				}else{
					arVerColor.add(1);
				}
				invalidate();
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item1:
			vw.changeColor();
			break;
		case R.id.item2:
			vw.clearCanvas();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}