package com.lcastr0.PrisonScoreboard.helper;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class Color {

    public String text;
    public ChatColor textColor;
    public ChatColor colorBefore;
    public ChatColor colorDuring;
    public ChatColor colorAfter;
    public ScrollType scrollType;

    private int i = 0;

    public Color(String text, ChatColor textColor, ChatColor colorBefore, ChatColor colorDuring, ChatColor colorAfter, ScrollType scrollType){
        this.text = text;
        this.textColor = textColor;
        this.colorBefore = colorBefore;
        this.colorDuring = colorDuring;
        this.colorAfter = colorAfter;
        this.scrollType = scrollType;
        if(scrollType == ScrollType.BACKWARDS_BLINK || scrollType == ScrollType.BACKWARDS)
            this.i = text.length();
    }

    public String next(){
        String r;
        boolean increase = true;
        int length = this.text.length();
        if(this.scrollType == ScrollType.FORWARDS) {
            if (i == 0) {
                r = this.colorAfter + this.text.substring(0, 1) + this.textColor + this.text.substring(1);
            } else if (i == 1) {
                r = this.colorDuring + this.text.substring(0, 1) + this.colorAfter + this.text.substring(1, 2) + this.textColor + this.text.substring(2);
            } else if (i == length) {
                r = this.textColor + this.text.substring(0, length - 2) + this.colorBefore + this.text.substring(length - 2, length - 1) + this.colorDuring +
                        this.text.substring(length - 1);
            } else if (i == length + 1) {
                r = this.textColor + this.text.substring(0, length - 1) + this.colorDuring + this.text.substring(length - 1);
                i = 0;
                increase = false;
            } else {
                r = this.textColor + this.text.substring(0, i - 2) + this.colorBefore + this.text.substring(i - 2, i - 1) + this.colorDuring + this.text.substring(i - 1, i) +
                        this.colorAfter + this.text.substring(i, i + 1) + this.textColor + this.text.substring(i + 1, length);
            }
        } else if(this.scrollType == ScrollType.FORWARDS_BLINK) {
            if (i == 0) {
                r = this.colorAfter + this.text.substring(0, 1) + this.textColor + this.text.substring(1);
            } else if (i == 1) {
                r = this.colorDuring + this.text.substring(0, 1) + this.colorAfter + this.text.substring(1, 2) + this.textColor + this.text.substring(2);
            } else if (i == length) {
                r = this.textColor + this.text.substring(0, length - 2) + this.colorBefore + this.text.substring(length - 2, length - 1) + this.colorDuring +
                        this.text.substring(length - 1);
            } else if (i == length + 1) {
                r = this.textColor + this.text.substring(0, length - 1) + this.colorDuring + this.text.substring(length - 1);
            } else if (i == length + 2) {
                r = this.colorBefore + this.text;
            } else if (i == length + 3) {
                r = this.colorDuring + this.text;
            } else if (i == length + 4) {
                r = this.colorAfter + this.text;
                i = 0;
                increase = false;
            } else {
                r = this.textColor + this.text.substring(0, i - 2) + this.colorBefore + this.text.substring(i - 2, i - 1) + this.colorDuring + this.text.substring(i - 1, i) +
                        this.colorAfter + this.text.substring(i, i + 1) + this.textColor + this.text.substring(i + 1, length);
            }
        } else if(this.scrollType == ScrollType.BACKWARDS) {
            if(i == length){
                r = this.textColor + this.text.substring(0, length - 1) + this.colorAfter + this.text.substring(length - 1);
            } else if(i == length - 1){
                r = this.textColor + this.text.substring(0, length - 2) + this.colorAfter + this.text.substring(length - 2, length - 1) + this.colorDuring +
                        this.text.substring(length - 1);
            } else if(i == 0){
                r = this.colorDuring + this.text.substring(0, 1) + this.colorBefore + this.text.substring(1, 2) + this.textColor + this.text.substring(2);
            } else if(i == -1){
                r = this.colorBefore + this.text.substring(0, 1) + this.textColor + this.text.substring(1);
                i = length;
                increase = false;
            } else {
                r = this.textColor + this.text.substring(0, i - 1) + this.colorAfter + this.text.substring(i - 1, i) + this.colorDuring + this.text.substring(i, i + 1) +
                        this.colorBefore + this.text.substring(i + 1, i + 2) + this.textColor + this.text.substring(i + 2, length);
            }
        } else if(this.scrollType == ScrollType.BACKWARDS_BLINK) {
            if(i == length){
                r = this.textColor + this.text.substring(0, length - 1) + this.colorAfter + this.text.substring(length - 1);
            } else if(i == length - 1){
                r = this.textColor + this.text.substring(0, length - 2) + this.colorAfter + this.text.substring(length - 2, length - 1) + this.colorDuring +
                        this.text.substring(length - 1);
            } else if(i == 0){
                r = this.colorDuring + this.text.substring(0, 1) + this.colorBefore + this.text.substring(1, 2) + this.textColor + this.text.substring(2);
            } else if(i == -1){
                r = this.colorBefore + this.text.substring(0, 1) + this.textColor + this.text.substring(1);
            } else if(i == -2){
                r = this.colorAfter + this.text;
            } else if(i == -3){
                r = this.colorDuring + this.text;
            } else if(i == -4){
                r = this.colorBefore + this.text;
                i = length;
                increase = false;
            } else {
                r = this.textColor + this.text.substring(0, i - 1) + this.colorAfter + this.text.substring(i - 1, i) + this.colorDuring + this.text.substring(i, i + 1) +
                        this.colorBefore + this.text.substring(i + 1, i + 2) + this.textColor + this.text.substring(i + 2, length);
            }
        } else {
            if(i == 0)
                r = this.textColor + this.text;
            else if(i == 1)
                r = this.colorBefore + this.text;
            else if(i == 2)
                r = this.colorDuring + this.text;
            else {
                r = this.colorAfter + this.text;
                i = 0;
                increase = false;
            }
        }
        if (increase && (this.scrollType == ScrollType.BACKWARDS_BLINK || this.scrollType == ScrollType.BACKWARDS))
            i--;
        else if(increase)
            i++;
        return r;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setTextColor(ChatColor color){
        this.textColor = color;
    }

    public void setColorBefore(ChatColor color){
        this.colorBefore = color;
    }

    public void setColorDuring(ChatColor color){
        this.colorDuring = color;
    }

    public void setColorAfter(ChatColor color){
        this.colorAfter = color;
    }

    public enum ScrollType{

        FORWARDS_BLINK(0),
        FORWARDS(1),
        BACKWARDS_BLINK(2),
        BACKWARDS(3),
        BLINK(4);

        public int i;
        private static Map<Integer, ScrollType> scrollTypes = new HashMap<>();

        static{
            for(ScrollType type : ScrollType.values())
                scrollTypes.put(type.i, type);
        }

        ScrollType(int i){
            this.i = i;
        }

        public static ScrollType getScroll(int i){
            return scrollTypes.get(i);
        }

    }
}
