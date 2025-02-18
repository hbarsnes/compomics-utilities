package com.compomics.util.io.export.features.peptideshaker;

import com.compomics.util.io.export.ExportFeature;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This enum lists the export features linked to the validation process.
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public enum PsValidationFeature implements ExportFeature {

    @SerializedName("PsValidationFeature.validated_protein")
    validated_protein("#Validated", "The number of validated proteins.", false),
    @SerializedName("PsValidationFeature.total_protein")
    total_protein("Total Possible TP", "The estimated total number of true positive proteins.", false),
    @SerializedName("PsValidationFeature.protein_fdr")
    protein_fdr("FDR Limit", "The estimated protein False Discovery Rate (FDR).", false),
    @SerializedName("PsValidationFeature.protein_fnr")
    protein_fnr("FNR Limit", "The estimated protein False Negative Rate (FNR).", false),
    @SerializedName("PsValidationFeature.protein_confidence")
    protein_confidence("Confidence Limit", "The lowest confidence among validated proteins.", false),
    @SerializedName("PsValidationFeature.protein_pep")
    protein_pep("PEP Limit", "The highest Posterior Error Probability (PEP) among validated proteins.", false),
    @SerializedName("PsValidationFeature.protein_accuracy")
    protein_accuracy("Confidence Accuracy", "The estimated protein Posterior Error Probability (PEP) and confidence estimation accuracy.", false),
    @SerializedName("PsValidationFeature.validated_peptide")
    validated_peptide("#Validated", "The number of validated peptides. Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.total_peptide")
    total_peptide("Total Possible TP", "The estimated total number of true positive peptides. Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.peptide_fdr")
    peptide_fdr("FDR Limit", "The estimated peptide False Discovery Rate (FDR). Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.peptide_fnr")
    peptide_fnr("FNR Limit", "The estimated peptide False Negative Rate (FNR). Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.peptide_confidence")
    peptide_confidence("Confidence Limit", "The lowest confidence among validated peptides. Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.peptide_pep")
    peptide_pep("PEP Limit", "The highest Posterior Error Probability (PEP) among validated peptides. Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.peptide_accuracy")
    peptide_accuracy("Confidence Accuracy", "The estimated peptide Posterior Error Probability (PEP) and confidence estimation accuracy. Note that peptides are grouped by modification status when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.validated_psm")
    validated_psm("#Validated PSM", "The number of validated Peptide Spectrum Matches (PSMs). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.total_psm")
    total_psm("Total Possible TP", "The estimated total number of true positive Peptide Spectrum Matches (PSMs). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.psm_fdr")
    psm_fdr("FDR Limit", "The estimated Peptide Spectrum Match (PSM) False Discovery Rate (FDR). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.psm_fnr")
    psm_fnr("FNR Limit", "The estimated Peptide Spectrum Match (PSM) False Negative Rate (FNR). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.psm_confidence")
    psm_confidence("Confidence Limit", "The lowest confidence among validated Peptide Spectrum Matches (PSMs). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.psm_pep")
    psm_pep("PEP Limit", "The highest Posterior Error Probability (PEP) among validated Peptide Spectrum Matches (PSMs). Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false),
    @SerializedName("PsValidationFeature.psm_accuracy")
    psm_accuracy("Confidence Accuracy", "The estimated Peptide Spectrum Match (PSM) Posterior Error Probability (PEP) and confidence estimation accuracy. Note that PSMs are grouped by identified charge when statistical significance is ensured, see advanced validation parameters.", false);

    /**
     * The title of the feature which will be used for column heading.
     */
    public String title;
    /**
     * The description of the feature.
     */
    public String description;
    /**
     * The type of export feature.
     */
    public final static String type = "Validation Summary";
    /**
     * Indicates whether a feature is for advanced user only.
     */
    private final boolean advanced;

    /**
     * Constructor.
     *
     * @param title title of the feature
     * @param description description of the feature
     * @param advanced indicates whether a feature is for advanced user only
     */
    private PsValidationFeature(String title, String description, boolean advanced) {
        this.title = title;
        this.description = description;
        this.advanced = advanced;
    }

    @Override
    public ArrayList<ExportFeature> getExportFeatures(boolean includeSubFeatures) {
        ArrayList<ExportFeature> result = new ArrayList<>();
        result.addAll(Arrays.asList(values()));
        return result;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFeatureFamily() {
        return type;
    }

    @Override
    public boolean isAdvanced() {
        return advanced;
    }
}
