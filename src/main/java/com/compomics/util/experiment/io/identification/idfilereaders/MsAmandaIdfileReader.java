package com.compomics.util.experiment.io.identification.idfilereaders;

import com.compomics.util.Util;
import com.compomics.util.experiment.biology.aminoacids.sequence.AminoAcidSequence;
import com.compomics.util.experiment.biology.proteins.Peptide;
import com.compomics.util.experiment.identification.Advocate;
import com.compomics.util.experiment.identification.spectrum_assumptions.PeptideAssumption;
import com.compomics.util.parameters.identification.search.SearchParameters;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.io.identification.IdfileReader;
import com.compomics.util.experiment.mass_spectrometry.SpectrumProvider;
import com.compomics.util.io.IoUtil;
import com.compomics.util.io.flat.SimpleFileReader;
import com.compomics.util.parameters.identification.advanced.SequenceMatchingParameters;
import com.compomics.util.waiting.WaitingHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.bind.JAXBException;

/**
 * This IdfileReader reads identifications from an MS Amanda csv result file.
 *
 * @author Harald Barsnes
 */
public class MsAmandaIdfileReader implements IdfileReader {

    /**
     * The software name.
     */
    private final String softwareName = "MS Amanda";
    /**
     * The softwareVersion.
     */
    private String softwareVersion = null;
    /**
     * The MS Amanda csv file.
     */
    private File msAmandaCsvFile;

    /**
     * Default constructor for the purpose of instantiation.
     */
    public MsAmandaIdfileReader() {
    }

    /**
     * Constructor for an MS Amanda csv result file reader.
     *
     * @param msAmandaCsvFile the MS Amanda csv file
     *
     * @throws FileNotFoundException if a FileNotFoundException occurs
     * @throws IOException if an IOException occurs
     */
    public MsAmandaIdfileReader(
            File msAmandaCsvFile
    ) throws IOException {
        this(msAmandaCsvFile, null);
    }

    /**
     * Constructor for an MS Amanda csv result file reader.
     *
     * @param msAmandaCsvFile the MS Amanda csv file
     * @param waitingHandler the waiting handler
     *
     * @throws FileNotFoundException if a FileNotFoundException occurs
     * @throws IOException if an IOException occurs
     */
    public MsAmandaIdfileReader(
            File msAmandaCsvFile,
            WaitingHandler waitingHandler
    ) throws IOException {

        this.msAmandaCsvFile = msAmandaCsvFile;

        // get the ms amanda version number
        extractVersionNumber();
    }

    /**
     * Extracts the MS Amanda version number.
     */
    private void extractVersionNumber() {

        try (SimpleFileReader reader = SimpleFileReader.getFileReader(msAmandaCsvFile)) {

            // read the version number, if available, requires ms amanda version 1.0.0.3196 or newer
            String versionNumberString = reader.readLine();

            if (versionNumberString.toLowerCase().startsWith("#version: ")) {
                softwareVersion = versionNumberString.substring("#version: ".length()).trim();
            }

        }

    }

    @Override
    public String getExtension() {
        return ".ms-amanda.csv";
    }

    @Override
    public ArrayList<SpectrumMatch> getAllSpectrumMatches(
            SpectrumProvider spectrumProvider,
            WaitingHandler waitingHandler,
            SearchParameters searchParameters
    )
            throws IOException, IllegalArgumentException, SQLException, ClassNotFoundException, InterruptedException, JAXBException {

        return getAllSpectrumMatches(
                spectrumProvider,
                waitingHandler,
                searchParameters,
                null,
                true
        );

    }

    @Override
    public ArrayList<SpectrumMatch> getAllSpectrumMatches(
            SpectrumProvider spectrumProvider,
            WaitingHandler waitingHandler,
            SearchParameters searchParameters,
            SequenceMatchingParameters sequenceMatchingPreferences,
            boolean expandAaCombinations
    )
            throws IOException, IllegalArgumentException, SQLException, ClassNotFoundException, InterruptedException, JAXBException {

        HashMap<String, HashMap<String, SpectrumMatch>> tempResults = new HashMap<>();

        try (SimpleFileReader reader = SimpleFileReader.getFileReader(msAmandaCsvFile)) {

            // check if the version number is included, ms amanda version 1.0.0.3196 or newer
            String versionNumberString = reader.readLine();
            String headerString;

            // skip the version number
            if (versionNumberString.toLowerCase().startsWith("#version: ")) {
                headerString = reader.readLine();
            } else {
                headerString = versionNumberString;
            }

            // parse the header line
            String[] headers = headerString.split("\t");

            int scanNumberIndex = -1,
                    titleIndex = -1,
                    sequenceIndex = -1,
                    modificationsIndex = -1,
                    proteinAccessionsIndex = -1,
                    amandaScoreIndex = -1,
                    rankIndex = -1,
                    mzIndex = -1,
                    chargeIndex = -1,
                    rtIndex = -1,
                    nrMatchesPeaksIndex = -1,
                    filenameIndex = -1,
                    amandaWeightedProbabilityIndex = -1,
                    missedCleavagesIndex = -1,
                    residuesIndex = -1,
                    fragmentIonsIndex = -1,
                    deltaMIndex = -1,
                    averageMs2ErrorIndex = -1,
                    assignedIntensityFractionIndex = -1,
                    binomScoreIndex = -1,
                    searchDepthIndex = -1,
                    idIndex = -1,
                    percolatorQValueIndex = -1;

            // get the column index of the headers
            for (int i = 0; i < headers.length; i++) {

                String header = headers[i];

                if (header.equalsIgnoreCase("Scan Number")) {
                    scanNumberIndex = i;
                } else if (header.equalsIgnoreCase("Title")) {
                    titleIndex = i;
                } else if (header.equalsIgnoreCase("Sequence")) {
                    sequenceIndex = i;
                } else if (header.equalsIgnoreCase("Modifications")) {
                    modificationsIndex = i;
                } else if (header.equalsIgnoreCase("Protein Accessions")) {
                    proteinAccessionsIndex = i;
                } else if (header.equalsIgnoreCase("Amanda Score")) {
                    amandaScoreIndex = i;
                } else if (header.equalsIgnoreCase("Weighted Probability")) {
                    amandaWeightedProbabilityIndex = i;
                } else if (header.equalsIgnoreCase("Rank")) {
                    rankIndex = i;
                } else if (header.equalsIgnoreCase("m/z")) {
                    mzIndex = i;
                } else if (header.equalsIgnoreCase("Charge")) {
                    chargeIndex = i;
                } else if (header.equalsIgnoreCase("RT")) {
                    rtIndex = i;
                } else if (header.equalsIgnoreCase("Nr of matched peaks")) {
                    nrMatchesPeaksIndex = i;
                } else if (header.equalsIgnoreCase("Filename")) {
                    filenameIndex = i;
                } else if (header.equalsIgnoreCase("number of missed cleavages")) {
                    missedCleavagesIndex = i;
                } else if (header.equalsIgnoreCase("number of residues")) {
                    residuesIndex = i;
                } else if (header.equalsIgnoreCase("number of considered fragment ions")) {
                    fragmentIonsIndex = i;
                } else if (header.equalsIgnoreCase("delta M")) {
                    deltaMIndex = i;
                } else if (header.equalsIgnoreCase("avg MS2 error[ppm]")) {
                    averageMs2ErrorIndex = i;
                } else if (header.equalsIgnoreCase("assigned intensity fraction")) {
                    assignedIntensityFractionIndex = i;
                } else if (header.equalsIgnoreCase("binom score")) {
                    binomScoreIndex = i;
                } else if (header.equalsIgnoreCase("SearchDepth")) {
                    searchDepthIndex = i;
                } else if (header.equalsIgnoreCase("Id")) {
                    idIndex = i;
                } else if (header.equalsIgnoreCase("percolator:Q value")) {
                    percolatorQValueIndex = i;
                }

            }

            // check if all the required header are found
            if (scanNumberIndex == -1 || titleIndex == -1 || sequenceIndex == -1 || modificationsIndex == -1
                    || proteinAccessionsIndex == -1 || amandaScoreIndex == -1 || rankIndex == -1
                    || mzIndex == -1 || chargeIndex == -1 || filenameIndex == -1) {
                throw new IllegalArgumentException("Mandatory columns are missing in the MS Amanda csv file. Please check the file!");
            }

            String line;

            // get the psms
            while ((line = reader.readLine()) != null) {

                String[] elements = line.split("\t");

                if (!line.trim().isEmpty()) { // @TODO: make this more robust?

                    //String scanNumber = elements[scanNumberIndex]; // not currently used
                    String spectrumTitle = elements[titleIndex].trim();
                    String peptideSequence = elements[sequenceIndex].toUpperCase();
                    String modifications = elements[modificationsIndex].trim();
                    //String proteinAccessions = elements[proteinAccessionsIndex]; // not currently used

                    // get the ms amanda score
                    String scoreAsText = elements[amandaScoreIndex];
                    double msAmandaRawScore = Util.readDoubleAsString(scoreAsText);
                    double msAmandaTransformedScore;

                    // get the ms amanda e-value
                    if (amandaWeightedProbabilityIndex != -1) {
                        String eVaulueAsText = elements[amandaWeightedProbabilityIndex];
                        msAmandaTransformedScore = Util.readDoubleAsString(eVaulueAsText);
                    } else {
                        msAmandaTransformedScore = Math.pow(10, -msAmandaRawScore); // convert ms amanda score to e-value like
                    }

                    int rank = Integer.parseInt(elements[rankIndex]);
                    //String mzAsText = elements[mzIndex]; // not currently used
                    //double mz = Util.readDoubleAsString(mzAsText);
                    int charge = Integer.parseInt(elements[chargeIndex]);
                    //String rtAsText = elements[rtIndex]; // not currently used, and not mandatory, as old csv files didn't have this one...
                    //double rt = Util.readDoubleAsString(rtAsText); // @TODO: have to escape retention times such as PT2700.460000S
                    String fileName = elements[filenameIndex];
                    //int missedCleavages = Integer.parseInt(elements[missedCleavagesIndex]); // not currently used
                    //int numberOfResidues = Integer.parseInt(elements[residuesIndex]); // not currently used
                    //int numberOfconsideredFragmentIons = Integer.parseInt(elements[fragmentIonsIndex]); // not currently used
                    //String deltaMAsText = elements[deltaMIndex]; // not currently used
                    //double deltaM = Util.readDoubleAsString(deltaMAsText);
                    //String averageMs2ErrorAsText = elements[averageMs2ErrorIndex]; // not currently used
                    //double averageMs2Error = Util.readDoubleAsString(averageMs2ErrorAsText);
                    //String assignedIntensityFractionAsText = elements[assignedIntensityFractionIndex]; // not currently used
                    //double assignedIntensityFraction = Util.readDoubleAsString(assignedIntensityFractionAsText);
                    //String binomScoreAsText = elements[binomScoreIndex]; // not currently used
                    //double binomScore = Util.readDoubleAsString(binomScoreAsText);  
                    //int searchDepth = searchDepthIndex != -1 ? Integer.parseInt(elements[searchDepthIndex]) : 1; // not currently used
                    //int msAmandaHitId = Integer.parseInt(elements[idIndex]); // not currently used
                    //String percolatorQValueAsText = elements[percolatorQValueIndex];
                    //if (percolatorQValueAsText.equalsIgnoreCase("NA")) {
                    //    double percolatorQValue = Util.readDoubleAsString(percolatorQValueAsText); // not currently used
                    //}

                    // remove any html from the title
                    spectrumTitle = URLDecoder.decode(spectrumTitle, "UTF-8");

                    // set up the yet empty spectrum match
                    if (!tempResults.containsKey(fileName)) {
                        tempResults.put(fileName, new HashMap<>());
                    }

                    if (!tempResults.get(fileName).containsKey(spectrumTitle)) {
                        tempResults.get(fileName).put(spectrumTitle, new SpectrumMatch(fileName, spectrumTitle));
                    }

                    // get the modifications
                    ArrayList<ModificationMatch> utilitiesModifications = new ArrayList<>(1);

                    if (!modifications.isEmpty()) {

                        String[] modificationsString = modifications.split(";");

                        for (String modificationString : modificationsString) {

                            try {
                                // we expect something like this:
                                // N-Term(acetylation of protein n-term|42.010565|variable) or
                                // C4(carbamidomethyl c|57.021464|fixed)

                                String location = modificationString.substring(0, modificationString.indexOf("("));
                                int modSite;

                                if (location.equalsIgnoreCase("N-Term")) {
                                    modSite = 1;
                                } else if (location.equalsIgnoreCase("C-Term")) {
                                    modSite = peptideSequence.length();
                                } else {
                                    // amino acid type and index expected, e.g., C4 or M3
                                    modSite = Integer.parseInt(modificationString.substring(1, modificationString.indexOf("(")));
                                }

                                String rest = modificationString.substring(modificationString.indexOf("(") + 1, modificationString.length() - 1).toLowerCase();

                                String[] details = rest.split("\\|");
                                String modName = details[0]; // not currently used
                                String modMassAsString = details[1];
                                double modMass = Util.readDoubleAsString(modMassAsString);
                                String modFixedStatus = details[2];

                                if (modFixedStatus.equalsIgnoreCase("variable")) {

                                    utilitiesModifications.add(
                                            new ModificationMatch(
                                                    modMass + "@" + peptideSequence.charAt(modSite - 1),
                                                    modSite
                                            )
                                    );

                                }

                            } catch (Exception e) {
                                throw new IllegalArgumentException("Error parsing modification: " + modificationString + ".");
                            }

                        }

                    }

                    // create the peptide
                    Peptide peptide = new Peptide(
                            peptideSequence,
                            utilitiesModifications.toArray(new ModificationMatch[utilitiesModifications.size()]),
                            true
                    );

                    // create the peptide assumption
                    PeptideAssumption peptideAssumption = new PeptideAssumption(
                            peptide,
                            rank, // @TODO: what to do about the rank..?
                            Advocate.msAmanda.getIndex(),
                            charge,
                            msAmandaRawScore,
                            msAmandaTransformedScore,
                            IoUtil.getFileName(msAmandaCsvFile)
                    );

                    if (expandAaCombinations && AminoAcidSequence.hasCombination(peptideSequence)) {

                        ModificationMatch[] previousModificationMatches = peptide.getVariableModifications();

                        for (StringBuilder expandedSequence : AminoAcidSequence.getCombinations(peptide.getSequence())) {

                            ModificationMatch[] newModificationMatches = Arrays.stream(previousModificationMatches)
                                    .map(modificationMatch -> modificationMatch.clone())
                                    .toArray(ModificationMatch[]::new);

                            Peptide newPeptide = new Peptide(expandedSequence.toString(), newModificationMatches, true);

                            PeptideAssumption newAssumption = new PeptideAssumption(
                                    newPeptide,
                                    peptideAssumption.getRank(),
                                    peptideAssumption.getAdvocate(),
                                    peptideAssumption.getIdentificationCharge(),
                                    peptideAssumption.getRawScore(),
                                    peptideAssumption.getScore(),
                                    peptideAssumption.getIdentificationFile()
                            );

                            tempResults.get(fileName).get(spectrumTitle).addPeptideAssumption(Advocate.msAmanda.getIndex(), newAssumption);

                        }

                    } else {

                        tempResults.get(fileName).get(spectrumTitle).addPeptideAssumption(Advocate.msAmanda.getIndex(), peptideAssumption);

                    }

                    if (waitingHandler != null && waitingHandler.isRunCanceled()) {

                        break;

                    }

                }

            }

        }

        ArrayList<SpectrumMatch> allSpectrumMatches = new ArrayList<>();

        for (String fileName : tempResults.keySet()) {

            for (String spectrumTitle : tempResults.get(fileName).keySet()) {

                allSpectrumMatches.add(tempResults.get(fileName).get(spectrumTitle));

            }

        }

        return allSpectrumMatches;
    }

    @Override
    public void close() throws IOException {
        msAmandaCsvFile = null;
    }

    @Override
    public HashMap<String, ArrayList<String>> getSoftwareVersions() {

        HashMap<String, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> versions = new ArrayList<>();
        versions.add(softwareVersion);
        result.put(softwareName, versions);

        return result;

    }

    @Override
    public boolean hasDeNovoTags() {
        return false;
    }
}
