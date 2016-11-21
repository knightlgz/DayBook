package k3.daybook.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Usage;

/**
 * @author Kyson LEE
 */

public class SortingUtil {

    private static final String TAG = "SortingUtil";

    /**
     * sort the usages with recommends
     * the last one selected usage will be sorted to the first
     * the most frequently used usage in the last 7 days will be the second
     * then the rest of them are sorted by the time they are used
     * while we got the same use times, sort by id
     * @param usageList the original list of usage
     * @return the sorted list of usage
     */
    public static void RecommendSortingUsage(List<Usage> usageList) {
        List<Usage> sorted = new ArrayList<>();
        Usage tem = DBUtil.getLastSelectedUsage();
        if (tem == null) {
            Log.d(TAG, "RecommendSorting: could not find last used usage");
        } else {
            sorted.add(tem);
            usageList.remove(tem);
        }

        tem = DBUtil.getLastWeekMostSelectedUsage();
        if (tem == null) {
            Log.d(TAG, "RecommendSorting: could not find the most time used usage in the last week");
        } else {
            sorted.add(tem);
            usageList.remove(tem);
        }
        sortUsageByTimes(usageList);
        sorted.addAll(usageList);
        usageList.clear();
        usageList.addAll(sorted);
    }

    private static void sortUsageByTimes(List<Usage> usageList) {
        for (int i = 0; i < usageList.size(); i++) {
            for (int j = 0; j < usageList.size() - 1; j++) {
                if (usageList.get(j).getTimes() < usageList.get(j + 1).getTimes()){
                    Usage tem = usageList.get(j);
                    usageList.set(j, usageList.get(j + 1));
                    usageList.set(j + 1, tem);
                }
            }
        }
    }

    /**
     * sort the payments with recommends
     * the last one selected payment will be sorted to the first
     * the most frequently used payment in the last 7 days will be the second
     * then the rest of them are sorted by the time they are used
     * while we got the same use times, sort by id
     * @param paymentList the original list of usage
     * @return the sorted list of usage
     */
    public static void RecommendSortingPayment(List<Payment> paymentList) {
        List<Payment> sorted = new ArrayList<>();
        Payment tem = DBUtil.getLastSelectedPayment();
        if (tem == null) {
            Log.d(TAG, "RecommendSorting: could not find last used payment");
        } else {
            sorted.add(tem);
            paymentList.remove(tem);
        }

        tem = DBUtil.getLastWeekMostSelectedPayment();
        if (tem == null) {
            Log.d(TAG, "RecommendSorting: could not find the most time used payment in the last week");
        } else {
            sorted.add(tem);
            paymentList.remove(tem);
        }
        sortPaymentByTimes(paymentList);
        sorted.addAll(paymentList);
        paymentList.clear();
        paymentList.addAll(sorted);
    }

    private static void sortPaymentByTimes(List<Payment> paymentList) {
        for (int i = 0; i < paymentList.size(); i++) {
            for (int j = 0; j < paymentList.size() - 1; j++) {
                if (paymentList.get(j).getTimes() < paymentList.get(j + 1).getTimes()){
                    Payment tem = paymentList.get(j);
                    paymentList.set(j, paymentList.get(j + 1));
                    paymentList.set(j + 1, tem);
                }
            }
        }
    }
}
