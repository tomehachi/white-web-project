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

    /**
     * セルの値を文字列に変換して取得する.
     *
     * @param cell セル
     * @param cellType セルの型
     * @return 文字列型に変換したセルの値.
     */
    private String convertToString(Cell cell, int cellType) {
        switch (cellType) {
        case Cell.CELL_TYPE_NUMERIC:
            double value = cell.getNumericCellValue();
            try {
                // 整数にならない値を parseInt すると NumberFormatException が発生する.
                // NumberFormatException が発生しなければ整数と判定.
                return String.valueOf(Integer.parseInt(String.valueOf(value)));

            } catch (NumberFormatException e) {
                return String.valueOf(value);
            }

        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();

        case Cell.CELL_TYPE_FORMULA:
            // 関数だったら関数の結果の型を取得し、再帰呼び出し.
            return convertToString(cell, cell.getCachedFormulaResultType());

        case Cell.CELL_TYPE_BLANK:
            return "";

        case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());

        case Cell.CELL_TYPE_ERROR:
            return "error";

        default:
            return "unknown type";
        }
    }

    /**
     * セルの値を文字列に変換して取得する.<br>
     *
     * @param sheetName シート名
     * @param rowNum 行番号
     * @param colNum 列番号
     * @return 文字列型に置換したセルの値
     */
    public String getCellValueAsString(String sheetName, int rowNum, int colNum) {
        Cell cell = getCell(sheetName, rowNum, colNum);
        if(cell != null) {
            return convertToString(cell, cell.getCellType());

        } else {
            return null;
        }
    }

    public String getFileName() {
        return this.fileName;
    }
}
