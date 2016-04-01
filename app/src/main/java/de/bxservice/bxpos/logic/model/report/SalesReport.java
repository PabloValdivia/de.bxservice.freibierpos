package de.bxservice.bxpos.logic.model.report;

import android.content.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.bxservice.bxpos.R;
import de.bxservice.bxpos.logic.DataProvider;
import de.bxservice.bxpos.logic.model.pos.POSOrder;
import de.bxservice.bxpos.logic.model.pos.POSOrderLine;

/**
 * Created by Diego Ruiz on 25/03/16.
 */
public class SalesReport extends Report {

    private List<POSOrder> paidOrders;
    private ReportHtmlTemplate htmlTemplate;

    public SalesReport(Context mContext) {
        super(mContext);
        htmlTemplate = new ReportHtmlTemplate();
    }

    @Override
    protected void performReport() {
        paidOrders = new DataProvider(mContext).getPaidOrders(fromDate, toDate);
    }

    /**
     * Set the result with the html template
     */
    @Override
    protected void setReportResult() {

        htmlResult.append(htmlTemplate.getHtmlTemplate().replace(ReportHtmlTemplate.TITLE_TAG, name));
        if(paidOrders != null) {

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            //Here is to remove the € sign because it has problems in HTML
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);

            BigDecimal totalSold   = BigDecimal.ZERO;
            BigDecimal totalVoided = BigDecimal.ZERO;
            //TODO add discounted

            htmlResult.append(htmlTemplate.getHtmlTableHeader());

            for(POSOrder order : paidOrders) {
                totalSold = totalSold.add(order.getTotallines());

                for (POSOrderLine orderLine : order.getOrderedLines()) {
                    if(orderLine.getLineStatus().equals(POSOrderLine.VOIDED))
                        totalVoided = totalVoided.add(orderLine.getLineNetAmt());
                }
            }

            //Net sales row
            htmlResult.append(htmlTemplate.getHtmlRowOpen());
            htmlResult.append(htmlTemplate.getHtmlColumn("left").replace(ReportHtmlTemplate.ROW_TAG, mContext.getString(R.string.net_sales)));
            htmlResult.append(htmlTemplate.getHtmlColumn("right").replace(ReportHtmlTemplate.ROW_TAG, currencyFormat.format(totalSold).trim() + " &euro;"));
            htmlResult.append(htmlTemplate.getHtmlRowClose());

            //Voided items row
            htmlResult.append(htmlTemplate.getHtmlRowOpen());
            htmlResult.append(htmlTemplate.getHtmlColumn("left").replace(ReportHtmlTemplate.ROW_TAG, mContext.getString(R.string.voided_items)));
            htmlResult.append(htmlTemplate.getHtmlColumn("right").replace(ReportHtmlTemplate.ROW_TAG, currencyFormat.format(totalVoided).trim() + " &euro;"));
            htmlResult.append(htmlTemplate.getHtmlRowClose());

            //Total row
            htmlResult.append(htmlTemplate.getHtmlRowOpen());
            htmlResult.append(htmlTemplate.getHtmlColumn("left").replace(ReportHtmlTemplate.ROW_TAG, mContext.getString(R.string.total)));
            htmlResult.append(htmlTemplate.getHtmlColumn("right").replace(ReportHtmlTemplate.ROW_TAG, currencyFormat.format(totalSold.subtract(totalVoided)).trim() + " &euro;"));
            htmlResult.append(htmlTemplate.getHtmlRowClose());


            //Close HTML table
            htmlResult.append(htmlTemplate.getHtmlTableClose());
        }
        else {
            htmlResult.append(htmlTemplate.getRowText().replace(ReportHtmlTemplate.ROW_TAG, mContext.getString(R.string.no_records)));
        }

    }
}