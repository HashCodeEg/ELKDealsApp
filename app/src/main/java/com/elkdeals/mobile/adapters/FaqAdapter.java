package com.elkdeals.mobile.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.FAQAnswer;
import com.elkdeals.mobile.api.models.FAQuestion;
import com.elkdeals.mobile.custom.JustifiedTextView;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqAdapter extends ExpandableRecyclerViewAdapter<FaqAdapter.QuestionViewHolder, FaqAdapter.AnswerViewHolder> {
    public FaqAdapter(List<? extends ExpandableGroup> groups) {
        super(groups, false);
    }

    @Override
    public QuestionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_faq_question, parent, false));
    }

    @Override
    public AnswerViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_faq_answer, parent, false));

    }

    @Override
    public void onBindChildViewHolder(AnswerViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        holder.bind((FAQAnswer) group.getItems().get(childIndex));

    }

    @Override
    public void onBindGroupViewHolder(QuestionViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.bind((FAQuestion) group);
    }

    public static class QuestionViewHolder extends GroupViewHolder {
        @BindView(R.id.question)
        TextView mQuestion;
        @BindView(R.id.arrow)
        ImageView arrow;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(FAQuestion question) {
            mQuestion.setText(question.getTitle());
            if (question.isExpanded())
                arrow.setImageResource(R.drawable.ic_collapse_question);
            else
                arrow.setImageResource(R.drawable.ic_expand_question);
        }
    }

    public static class AnswerViewHolder extends ChildViewHolder {
        @BindView(R.id.answer)
        JustifiedTextView mAnswer;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(FAQAnswer answer) {
            mAnswer.setText(Html.fromHtml(answer.getAnswer()+"<br>"));
        }
    }
}
