package com.elkdeals.mobile.api.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Collections;

public class FAQuestion extends ExpandableGroup<FAQAnswer> {

    public FAQuestion(String question, FAQAnswer answer) {
        super(question, Collections.singletonList(answer));
    }

}
