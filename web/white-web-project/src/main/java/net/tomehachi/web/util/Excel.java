package net.tomehachi.web.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel {

    private Workbook workbook;
    private String fileName;

    /**
     * 物理ファイルパスからExcelオブジェクト生成.<br>
     *
     * @param filePath ファイルフルパス
     * @throws AppException
     */
    public Excel(String filePath) throws AppException {
        File file = new File(filePath);
        this.fileName = file.getName();
        try {
            this.workbook = WorkbookFactory.create(file);

        } catch (EncryptedDocumentException e) {
            throw new AppException("暗号化されていて開けませんでした.", e);

        } catch (InvalidFormatException e) {
            throw new AppException("認識できないファイルフォーマットでした.", e);

        } catch (IOException e) {
            throw new AppException("入出力例外発生.", e);
        }
    }

    /**
     * ファイル入力ストリームからExcelオブジェクト生成.<br>
     *
     * @param in 入力ストリーム
     * @param fileName ファイル名
     * @throws AppException
     */
    public Excel(InputStream in, String fileName) throws AppException {
        try {
            this.fileName = fileName;
            this.workbook = WorkbookFactory.create(in);

        } catch (EncryptedDocumentException e) {
            throw new AppException("暗号化されていて開けませんでした.", e);

        } catch (InvalidFormatException e) {
            throw new AppException("認識できないファイルフォーマットでした.", e);

        } catch (IOException e) {
            throw new AppException("入出力例外発生.", e);
        }
    }

    public Sheet getSheet(String sheetName) {
        return this.workbook.getSheet(sheetName);
    }

    /**
     * 特定のセルのオブジェクトを取得.<br>
     *
     * @param sheetName
     * @param rowNum
     * @param colNum
     * @return
     */
    public Cell getCell(String sheetName, int rowNum, int colNum) {
        Sheet sheet = getSheet(sheetName);
        if(sheet == null) {
            return null;
        }
        Row row = sheet.getRow(rowNum);
        if(row == null) {
            return null;
        }
        return row.getCell(colNum);
    }

    public String getCellValueAsString(String sheetName, int rowNum, int colNum) {
        Cell cell = getCell(sheetName, rowNum, colNum);
        if(cell != null) {
            //TODO
            return null;

        } else {
            return null;
        }
    }
}
