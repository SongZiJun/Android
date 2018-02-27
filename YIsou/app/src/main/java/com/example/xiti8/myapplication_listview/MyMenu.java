package com.example.xiti8.myapplication_listview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.PipedOutputStream;
import java.lang.reflect.Type;
import java.nio.channels.ClosedByInterruptException;
import java.util.logging.XMLFormatter;

/**
 * Created by xiti8 on 2018/2/22.
 */

public class MyMenu extends ViewGroup implements View.OnClickListener{
    private Position myPosition = Position.RIGHT_BOTTOM;//菜单位置
    private int myRadius;   //菜单半径
    private Status myCurrentStatus  = Status.CLOSE;//菜单状态
    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;
    public MyMenu(Context context) {
        this(context,null);
    }
    public MyMenu(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }
    public MyMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //半径
        myRadius = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100,getResources().getDisplayMetrics());
        //获取自定义属性 位置

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyMenu,defStyleAttr,0);
        int pos = a.getInt(R.styleable.MyMenu_position,POS_RIGHT_BOTTOM);
        switch (pos)
        {
            case POS_LEFT_TOP:
                myPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                myPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                myPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                myPosition = Position.RIGHT_BOTTOM;
                break;
        }
        myRadius = (int)a.getDimension(R.styleable.MyMenu_radius,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100,getResources().getDisplayMetrics()));
        //自定义属性成功
        //Log.e("s","position"+myPosition+"radius"+myRadius);
        a.recycle();
    }


    /*
                菜单位置
             */
    public enum Position
    {
        LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
    }
    /*
    菜单状态
     */
    public enum Status
    {
        OPEN,CLOSE
    }
    /*
        菜单主按钮
     */
    private View mCButtom;
    private OnMenuItemClickListener myOnMenuItemClickListener;
    /*
    点击子菜单下的回调接口
     */
    public interface OnMenuItemClickListener
    {
        void onClick(View view,int pos);
    }

    public void setMyOnMenuItemClickListener(OnMenuItemClickListener myOnMenuItemClickListener) {
        this.myOnMenuItemClickListener = myOnMenuItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            //测量child
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    //定位子菜单
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if(b)
        {
            layoutCButton();
            int count = getChildCount();
            for (int ii = 0;ii < count - 1;ii++)
            {
                View child = getChildAt(ii+1);

                //一开始默认调用不显示,点击按钮时候在显示出来
                child.setVisibility(View.GONE);
                //计算四个点
                int ci = (int) (myRadius * Math.sin(Math.PI/2/(count-2) * ii));
                int ci1 = (int)(myRadius * Math.cos(Math.PI/2/(count-2)* ii));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();
                //当位置在左下或者是右下
                if(myPosition == Position.LEFT_BOTTOM||myPosition == Position.RIGHT_BOTTOM)
                {
                    ci1 = getMeasuredHeight() - cHeight - ci1;
                }
                if(myPosition == Position.RIGHT_TOP||myPosition == Position.RIGHT_BOTTOM)
                {
                    ci = getMeasuredWidth() - cWidth - ci;
                }
                child.layout(ci,ci1,ci+cWidth,ci1+cHeight);
            }
        }
    }
    /*
        定位首菜单按钮位置
     */
    private void layoutCButton() {
        mCButtom = getChildAt(0);
        mCButtom.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = mCButtom.getMeasuredWidth();
        int height = mCButtom.getMeasuredHeight();
        switch (myPosition)
        {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButtom.layout(l,t,l+width,t+width);




    }

    @Override
    public void onClick(View view) {
        rotateCButton( view ,0f,360f,300);
        toggleMenu(300);
    }
    /*
        切换菜单
     */
    public void toggleMenu(int duration)
    {
        //为子菜单添加平移动画和旋转动画
        int count = getChildCount();
        for(int i = 0; i < count - 1;i++)
        {
            final View childView = getChildAt(i+1);
            childView.setVisibility(View.VISIBLE);//平移动画

            int ci = (int) (myRadius * Math.sin(Math.PI/2/(count-2) * i));
            int ci1 = (int)(myRadius * Math.cos(Math.PI/2/(count-2)* i));
            int xflag = 1;
            int yflag = 1;
            //确定起始位置
            if(myPosition == Position.LEFT_TOP||myPosition == Position.LEFT_BOTTOM)
            {
                xflag = -1;
            }
            if(myPosition == Position.LEFT_TOP||myPosition == Position.RIGHT_TOP)
            {
                xflag = -1;
            }

            AnimationSet animset = new AnimationSet(true);
            Animation tranAnim = null;
            //判断此时子按钮的状态是隐藏还是显示
            if(myCurrentStatus == Status.CLOSE)
            {
                //展开状态
                tranAnim = new TranslateAnimation(
               xflag*ci,0,yflag*ci1,0
            );
                childView.setClickable(true);
                childView.setFocusable(true);

            }else
                {
                    //隐藏状态
                     tranAnim = new TranslateAnimation(
                             0,xflag*ci,0,yflag*ci1
                );
                     //此时的状态按钮可以点击
                    childView.setClickable(false);
                    childView.setFocusable(false);
            }

            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i*100)/count);//控制子菜单每个按不同的速度打开
            //设置动画监听
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(myCurrentStatus == Status.CLOSE)
                    {
                        childView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //子菜单旋转动画实现
            RotateAnimation rotateAnim =  new RotateAnimation(
                0,720, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
            );
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

            animset.addAnimation(rotateAnim);//增加旋转
            animset.addAnimation(tranAnim);//增加移动
            childView.startAnimation(animset);

            //设置子菜单点击事件
            final int  pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(myOnMenuItemClickListener!=null)
                        myOnMenuItemClickListener.onClick(childView,pos);
                    //回调动画
                    menuItemAnim(pos-1);
                    changeStatus();
                }
            });

        }
        //切换菜单状态
        changeStatus();
    }
    /*
        添加子菜单的点击动画
     */
    private void menuItemAnim(int pos) {
        for(int i = 0; i< getChildCount() - 1;i++)
        {
            View childView = getChildAt(i+1);
            if(i==pos)
            {
                childView.startAnimation(scaleBigAnim(300));//点击变大
            }else {

                childView.startAnimation(scaleSmallAnim(300));//没有被点击的变小
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    /*
        没有点击的子菜单透明度降低
     */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f,0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }
    /*
      当子菜单被点击时图标变大
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f,4.0f,1.0f,4.0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f,0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /*
    //切换菜单状态
     */
    private void changeStatus() {
        //首先判断现在是关闭吗？如果关闭打开，否则关闭
        myCurrentStatus =
                (myCurrentStatus == Status.CLOSE?Status.OPEN:Status.CLOSE);

    }

    /*
        控制主菜单的动效（主按钮旋转）
     */
    private void rotateCButton(View v,float start,float end,int duration)
    {
        RotateAnimation anim =
                new RotateAnimation(
                        start,end, Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f
                        );
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}
