class Tracsaction {

    public long findSmallestReserve(List<List<Long>> M) {

        Long lowR = 0;
        Long highR = 0;
        if (rec.get(1) == 0) {
            high += rec.get(2);
        }
        if (canSurvive(0L, M)) {
            return 0L;
        }

        while ( highR > lowR ) {
            Long mid = lowR + ( highR - lowR ) / 2;
            if ( canSurvive(mid, M) ) {
                highR = mid ;
            } else {
                lowR = mid + 1;
            }
        }

        return lowR;
    }

    public boolean canSurvive( long R, List<List<Long>> transactions ) {
        Long preday = 1;
        for (List<Long> rec : transactions) {
            Long day = rec.get(0);
            Long type = rec.get(1);
            Long amt = rec.get(2);

            // CAlCulae preday close Ballance
            // interest on 5th preday
            if (preDay != day && preDay % 5 == 0 ) {
                R += R / 5;
            }

            // calculate missing day with no transaction - between preday and day
            for( Long date = preday+1; date < day; date++ ) {
                if (date % 5 == 0) {
                    R += R / 5 ;
                }
            }
           
            // calculate dif of day
            if ( type == 1L ) {
                R += amt;
            } else if ( type == 0L ){
                R -= amt;
                if ( R < 0L) return false;
            } 

            preday = day ;
        }
        return true;
    }

    // better can Survive
    class Transaction {

    public long findSmallestReserve(List<List<Long>> transactions) {

        long low = 0;
        long high = 0;

        // Starting with enough money to pay every expense
        // certainly guarantees survival.
        for (List<Long> transaction : transactions) {
            if (transaction.get(1) == 0L) {
                high += transaction.get(2);
            }
        }

        while (low < high) {

            long mid = low + (high - low) / 2;

            if (canSurvive(mid, transactions)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private boolean canSurvive(
            long reserve,
            List<List<Long>> transactions) {

        if (transactions.isEmpty()) {
            return true;
        }

        long balance = reserve;
        long previousDay = 0;

        int i = 0;

        while (i < transactions.size()) {

            long currentDay = transactions.get(i).get(0);

            // Process interest days between previous transaction day
            // and current transaction day.
            for (long day = previousDay + 1; day < currentDay; day++) {
                if (day % 5 == 0) {
                    balance += balance / 5;
                }
            }

            // Process ALL transactions of currentDay
            while (i < transactions.size()
                    && transactions.get(i).get(0) == currentDay) {

                List<Long> transaction = transactions.get(i);

                long type = transaction.get(1);
                long amount = transaction.get(2);

                if (type == 1) {
                    balance += amount;
                } else {
                    balance -= amount;

                    if (balance < 0) {
                        return false;
                    }
                }

                i++;
            }

            // Interest happens AFTER every transaction that day.
            if (currentDay % 5 == 0) {
                balance += balance / 5;
            }

            previousDay = currentDay;
        }

        return true;
    }
}
}

private static boolean canSurvive(
        List<List<Long>> transactions,
        long reserve,
        int lastDay) {

    long balance = reserve;
    int i = 0;

    for (int day = 1; day <= lastDay; day++) {

        while (i < transactions.size()
                && transactions.get(i).get(0) == day) {

            List<Long> tx = transactions.get(i);

            long type = tx.get(1);
            long amount = tx.get(2);

            if (type == 1) {
                balance += amount;
            } else {
                balance -= amount;

                if (balance < 0) {
                    return false;
                }
            }

            i++;
        }

        if (day % 5 == 0) {
            balance += balance / 5;
        }
    }

    return true;
}