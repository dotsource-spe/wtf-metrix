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
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.data.LineFeedbackEntry;
import de.dotsource.wtf.service.FeedbackService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WtfExternalAnnotator extends ExternalAnnotator<FileMetricEntry, FileMetricEntry> {
    private static final Logger LOG = LoggerFactory.getLogger(WtfExternalAnnotator.class);
    private static final TextAttributesKey WTF = TextAttributesKey.createTextAttributesKey("WTF_WTF");
    private Document document;

    @Nullable
    @Override
    public FileMetricEntry collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
        return collectInformation(file);
    }

    @Nullable
    @Override
    public FileMetricEntry collectInformation(@NotNull PsiFile file) {
        document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        return FeedbackService.getInstance(file.getProject()).getFeedbackForFile(file.getVirtualFile().getPath());
    }

    @Nullable
    @Override
    public FileMetricEntry doAnnotate(FileMetricEntry collectedInfo) {
        return collectedInfo;
    }

    @Override
    public void apply(@NotNull PsiFile file, FileMetricEntry annotationResult, @NotNull AnnotationHolder holder) {
        annotationResult.forEach((lineNumber, lineEntry) -> addAnnotation(lineNumber, lineEntry, holder));
    }

    private void addAnnotation(Integer key, LineFeedbackEntry value, AnnotationHolder holder) {
        Annotation annotation = holder.createAnnotation(HighlightSeverity.ERROR, getTextRangeForLine(key), "WTF" + value.getRating(), "WTF!");
        annotation.setTextAttributes(WTF);
    }

    @NotNull
    private TextRange getTextRangeForLine(Integer lineNumber) {
        return new TextRange(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber));
    }
}
