package de.dotsource.wtf.intellij.editor;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.data.LineFeedbackEntry;
import de.dotsource.wtf.service.FeedbackService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class WtfExternalAnnotator extends ExternalAnnotator<FileMetricEntry, FileMetricEntry> {
    private static final Logger LOG = LoggerFactory.getLogger(WtfExternalAnnotator.class);
    private static final TextAttributesKey WTF = TextAttributesKey.createTextAttributesKey("WTF_WTF");
    private static final Random random = new Random();
    private Document document;
    private FeedbackService feedbackService = new FeedbackService() {
        @Override
        public FileMetricEntry storeFeedback(FeedbackEntry entry) {
            LOG.error("feedback stored.");
            return new FileMetricEntry();
        }

        @Override
        public FileMetricEntry getFeedbackForFile(String Path) {
            LOG.error("feedback loaded.");
            return generateFileMetricEntry(lines);
        }
    };

    static {
        LOG.warn("Class loaded.");
    }

    private int lines;

    private static FileMetricEntry generateFileMetricEntry(int lines) {
        FileMetricEntry fme = new FileMetricEntry();
        for (int i = 0; i < lines; i++) {
            fme.put(i, generateLineEntry(i+1));
        }
        return fme;
    }

    private static LineFeedbackEntry generateLineEntry(int linenumber) {
        return new LineFeedbackEntry(random.nextDouble(), Double.valueOf(random.nextDouble()).toString());
    }

    public WtfExternalAnnotator() {
        LOG.warn("Class instantiated.");
    }

    @Nullable
    @Override
    public FileMetricEntry collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
        return collectInformation(file);
    }

    @Nullable
    @Override
    public FileMetricEntry collectInformation(@NotNull PsiFile file) {
        lines = PsiDocumentManager.getInstance(file.getProject()).getDocument(file).getLineCount();
        document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        return feedbackService.getFeedbackForFile(file.getVirtualFile().getPath());
    }

    @Nullable
    @Override
    public FileMetricEntry doAnnotate(FileMetricEntry collectedInfo) {
        return collectedInfo;
    }

    @Override
    public void apply(@NotNull PsiFile file, FileMetricEntry annotationResult, @NotNull AnnotationHolder holder) {
        annotationResult.entrySet().forEach(lineEntry -> addAnnotation(lineEntry.getKey(), lineEntry.getValue(), holder));
    }

    private void addAnnotation(Integer key, LineFeedbackEntry value, AnnotationHolder holder) {
        Annotation annotation = holder.createAnnotation(HighlightSeverity.ERROR, getTextRangeForLine(key+1), "WTF" + value.getRating(), "WTF!");
    }

    @NotNull
    private TextRange getTextRangeForLine(Integer lineNumber) {
        return new TextRange(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber));
    }
}
