package budget;


import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
this class is for setting the word document of the budget
and because the apis that offer creating and modifying the
word document use waterMark or  don't have a lot of
customisability options specially when it comes to tables
the idea changed to have a hardcoded xml representation
of the word document then filling the tables of the document
with the associated values at runtime and then zipping the files
that make up the docx file which are _rels ,customXml ,docProps ,word ,[Content_Types].xml
 and changing the produced file extension to get a modified docx file
 */

public class BudgetDoc {
    private final Budget budget;

    public BudgetDoc(Budget budget) {
        this.budget = budget;

            }

    public File createDoc() throws Exception {
        File outPutFile;//modified xml file
        File xmlFile;//hardcoded xml file
        String path;
        //setting the right path for the hardcoded xml file depending on whether the company has profits or not
        if (this.budget.isHasProfits()){
            xmlFile = new File("hardCodedDocument2.xml");
            outPutFile = new File("word/document.xml");
        }else{
            xmlFile = new File("hardCodedDocument.xml");
            outPutFile = new File("word/document.xml");
        }
        FileWriter fileWriter = new FileWriter(outPutFile);
        FileInputStream stream = new FileInputStream(xmlFile.getAbsolutePath());
        InputStreamReader input = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(input);
        String newString ;
        // filling the tables with the values associated with the user's company
        while ((newString = reader.readLine()) != null) {
            if (this.budget.isHasProfits()){
                if (newString.contains("111")) {
                    fileWriter.write(newString.replaceAll("111",String.valueOf(this.budget.getProfits()*0.25)) + "\n");
                }else if (newString.contains("112")){
                    fileWriter.write(newString.replaceAll("112",String.valueOf(this.budget.getProfits()*0.5)) + "\n");
                }else if (newString.contains("1112")){
                    fileWriter.write(newString.replace("1112",String.valueOf(this.budget.getProfits())));
                }
            }
            if (newString.contains("2019")) {
                fileWriter.write(newString.replaceAll("2019", this.budget.getYear()) + "\n");
            } else if (newString.contains("companyName")) {
                fileWriter.write(newString.replaceAll("companyName", this.budget.getCompanyName()) + "\n");
            } else if (newString.contains("15000")) {
                fileWriter.write(newString.replaceAll("15000", String.valueOf(this.budget.getCapital())) + "\n");
            } else if (newString.contains("3450")) {
                fileWriter.write(newString.replaceAll("3450", String.valueOf(this.budget.getCreditors())) + "\n");
            } else if (newString.contains("1150")) {
                if (this.budget.isHasProfits()) {
                    fileWriter.write(newString.replaceAll("1150", String.valueOf(this.budget.getProfits())) + "\n");
                }
                else {
                    fileWriter.write(newString.replaceAll("1150", String.valueOf(this.budget.getLosses())) + "\n");
                }
            } else if (newString.contains("19600")) {
                if (this.budget.isHasProfits()){
                    fileWriter.write(newString.replaceAll("19600", String.valueOf(
                            this.budget.getCreditors() + this.budget.getCapital() ))+"\n");
                }else{
                    fileWriter.write(newString.replaceAll("19600", String.valueOf(
                            this.budget.getLosses() + this.budget.getCapital() + this.budget.getCreditors())) + "\n");
                }
            } else if (newString.contains("11080")) {
                fileWriter.write(newString.replaceAll("11080", String.valueOf(this.budget.getBank())) + "\n");
            } else if (newString.contains("3920")) {
                fileWriter.write(newString.replaceAll("3920", String.valueOf(this.budget.getTreasury())) + "\n");
            } else if (newString.contains("4600")) {
                fileWriter.write(newString.replaceAll("4600", String.valueOf(this.budget.getDebtors())) + "\n");
            } else if (newString.contains("30680")) {
                fileWriter.write(newString.replaceAll("30680", String.valueOf(this.budget.getIncome())) + "\n");
            } else if (newString.contains("29530")) {
                fileWriter.write(newString.replaceAll("29530", String.valueOf(this.budget.getIncome() - this.budget.getLosses())) + "\n");
            } else if (newString.contains("810")) {
                fileWriter.write(newString.replaceAll("810", String.valueOf(this.budget.getPublicExpenses())) + "\n");
            } else if (newString.contains("340")) {
                fileWriter.write(newString.replaceAll("340", String.valueOf(this.budget.getBankBenefits())) + "\n");
            } else {
                if (!(newString.contains("111")||newString.contains("112"))){
                    fileWriter.write(newString + "\n");
                }
            }
        }
        fileWriter.close();
        //adding files to the list to be zipped
        ArrayList<File> stringArrayList = new ArrayList<>();
        stringArrayList.add(new File(xmlFile.getParent()+ "/word"));
        stringArrayList.add(new File(xmlFile.getParent()+ "/customXml"));
        stringArrayList.add(new File(xmlFile.getParent()+ "/docProps"));
        stringArrayList.add(new File(xmlFile.getParent()+ "/_rels"));
        stringArrayList.add(new File(xmlFile.getParent()+ "/[Content_Types].xml"));
        File zipFile = new File(xmlFile.getParent()+"myDoc.zip");
        zipFiles(stringArrayList,zipFile.getAbsolutePath());
        //zipping the files and changing the produced file's extension
        File docxFile = new File(zipFile.getAbsolutePath().replace("zip","docx"));
        zipFile.renameTo(docxFile);
        return docxFile;
    }

    private void zipFiles(ArrayList<File> files, String destZipFile) throws Exception {
        if (files.size() > 0) {
            ZipOutputStream zip = null;
            FileOutputStream fileWriter = null;

            fileWriter = new FileOutputStream(destZipFile);
            zip = new ZipOutputStream(fileWriter);
            for (File f : files) {
                if (f.getAbsolutePath().contains("null/")) {
                    addFileToZip("", f.getAbsolutePath().replace("null/",""), zip);
                }
            }
            zip.flush();
            zip.close();
        }
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }
}

