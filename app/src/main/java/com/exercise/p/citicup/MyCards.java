package com.exercise.p.citicup;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/8/11.
 */

public class MyCards<E extends MyCards.MyTextCard> {

    private List<E> cards;

    public MyCards(List<E> cards) {
        this.cards = cards;
    }

    public List<E> getCards() {
        return cards;
    }

    public void setCards(List<E> cards) {
        this.cards = cards;
    }

    public List<String> getSelectedText() {
        List<String> list = new ArrayList<>();
        for (MyTextCard card : cards) {
            if (card.isSelected())
                list.add(card.getText());
        }
        return list;
    }

    public List<String> getAllText() {
        List<String> list = new ArrayList<>();
        for (MyTextCard card : cards) {
            list.add(card.getText());
        }
        return list;
    }

    public static class MyTextCard {
        private TextView textView;
        private boolean isSelected;

        public MyTextCard(final TextView view, final boolean selected) {
            this.textView = view;
            this.isSelected = selected;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected()) {
                        setSelected(false);
                    } else {
                        setSelected(true);
                    }
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public void setAppearance(int background, int textColor) {
            textView.setBackgroundResource(background);
            textView.setTextColor(textColor);
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getText() {
            return textView.getText().toString();
        }

        public void setOnclickListener(View.OnClickListener listener) {
            textView.setOnClickListener(listener);
        }
    }
}
