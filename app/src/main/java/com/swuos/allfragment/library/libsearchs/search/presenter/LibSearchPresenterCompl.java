package com.swuos.allfragment.library.libsearchs.search.presenter;

import android.content.Context;

import com.swuos.allfragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.allfragment.library.libsearchs.search.model.net.LibApi;
import com.swuos.allfragment.library.libsearchs.search.model.parse.LibParse;
import com.swuos.allfragment.library.libsearchs.search.view.ILibSearchView;
import com.swuos.swuassistant.Constant;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by 张孟尧 on 2016/9/5.
 */

public class LibSearchPresenterCompl implements ILibSearchPresenter {
    private Context context;
    private ILibSearchView libSearchview;
    private List<SearchBookItem> searchBookItemList = new ArrayList<>();
    private Subscriber subscriber1;
    private Subscriber subscriber2;

    public List<SearchBookItem> getSearchBookItemList() {
        return searchBookItemList;
    }

    public LibSearchPresenterCompl(Context context, ILibSearchView iLibSearchView) {
        this.context = context;
        this.libSearchview = iLibSearchView;


    }

    @Override
    public void SearchMore(final String bookName) {
        subscriber1 = new Subscriber<List<SearchBookItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                String error=throwable.getMessage();
                if (throwable instanceof UnknownHostException) {
                    error = Constant.CLIENT_ERROR;
                } else if (throwable instanceof SocketTimeoutException) {
                    error = Constant.CLIENT_TIMEOUT;
                }

                libSearchview.ShowError(error);
            }

            @Override
            public void onNext(List<SearchBookItem> searchBookItems) {
                searchBookItemList.addAll(searchBookItems);
                libSearchview.ShowResult(searchBookItemList);
            }
        };
        final Map<String, String> data = new HashMap<>();
        data.put("__EVENTTARGET", "");
        data.put("__EVENTARGUMENT", "");
        data.put("__LASTFOCUS", "");
        data.put("__VIEWSTATE", "/wEPDwUKLTIzODUwODY1Nw9kFgICAw9kFgICBQ9kFgJmD2QWFAIBD2QWBAIBDxYCHglpbm5lcmh0bWwFDOWMl+S6rOmHkeebmGQCAw8PFgIeBFRleHQF1wQ8dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nZGVmYXVsdC5hc3B4Jz48c3Bhbj7pppbpobU8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J2RlZmF1bHQuYXNweCc+PHNwYW4+5Lmm55uu5p+l6K+iPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdNYWdhemluZUNhbnRvU2NhcmNoLmFzcHgnPjxzcGFuPuacn+WIiuevh+WQjTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nUmVzZXJ2ZWRMaXN0LmFzcHgnPjxzcGFuPumihOe6puWIsOmmhjwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nRXhwaXJlZExpc3QuYXNweCc+PHNwYW4+6LaF5pyf5YWs5ZGKPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdOZXdCb29LU2NhcmNoLmFzcHgnPjxzcGFuPuaWsOS5pumAmuaKpTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nUmVhZGVyTG9naW4uYXNweCc+PHNwYW4+6K+76ICF55m75b2VPC9zcGFuPjwvQT48L3RkPmRkAgMPDxYCHwEFBumHkeebmGRkAgcPZBYEAgIPDxYCHwEFMjxzcGFuPuasoui/juaCqDpHdWVzdCDor7fpgInmi6nkvaDnmoTmk43kvZw8L3NwYW4+ZGQCAw8PFgIeB1Zpc2libGVoZGQCDg9kFgJmDw8WAh8BBe8P54Ot6Zeo5qOA57SiOjxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5bmz5Yeh55qE5LiW55WMJyk7Ij4g5bmz5Yeh55qE5LiW55WMPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn57qi5qW85qKmJyk7Ij4g57qi5qW85qKmPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5Zu05Z+OJyk7Ij4g5Zu05Z+OPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5b+D55CG5a2mJyk7Ij4g5b+D55CG5a2mPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgnU1BTUycpOyI+IFNQU1M8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfnmb7lubTlraTni6wnKTsiPiDnmb7lubTlraTni6w8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfniLHnmoTmlZnogrInKTsiPiDniLHnmoTmlZnogrI8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCflj7LorrAnKTsiPiDlj7LorrA8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfmoqbnmoTop6PmnpAnKTsiPiDmoqbnmoTop6PmnpA8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfov73po47nrZ3nmoTkuronKTsiPiDov73po47nrZ3nmoTkuro8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006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5Lmm44CB6L+HJei1hOaWmeWupC3mlofnjK7miYDvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeaVmeiCsuWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5aSW6K+t5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mlL/nrqHpmaLvvIjkuabjgIHov4fliIrvvIki6LWE5paZ5a6kLeiuoeenkemZou+8iOS5puOAgei/h++8iSjpmaLotYTmlpnlrqQt5Zyw55CG5a2m6Zmi77yI5Lmm44CB6L+H77yJIui1hOaWmeWupC3mlbDnu5/pmaLvvIjkuabjgIHov4fvvIkl6LWE5paZ5a6kLeiNr+WtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5L2T6IKy5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mlZnnp5HmiYDvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeWOhuWPsuWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5YyW5bel5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mloflrabpmaLvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeW/g+eQhuWtpumZou+8iOS5puOAgei/h+WIiu+8iSvotYTmlpnlrqQt55Sf5ZG956eR5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJKOi1hOaWmeWupC3pn7PkuZDlrabpmaLvvIjkuabjgIHov4fliIrvvIk06LWE5paZ5a6kLeaWh+enkeWfuuWcsOilv+awkeS4reW/g++8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt576O5pyv5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJIui1hOaWmeWupC3nlLXlrZDkv6Hmga/lt6XnqIvlrabpmaIo6LWE5paZ5a6kLeWfueiureWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5paw6K+X56CU56m25omA77yI5Lmm44CB6L+H5YiKKOi1hOaWmeWupC3niannkIblrabpmaLvvIjkuabjgIHov4fliIrvvIku6LWE5paZ5a6kLeS5oeadkeW7uuiuvuWtpumZou+8iOS5puOAgei/h+WIiu+8iSXotYTmlpnlrqQt5Yqo56eR6Zmi77yI5Lmm44CB6L+H5YiK77yJKOi1hOaWmeWupC3nurrnu4flrabpmaLvvIjkuabjgIHov4fliIrvvIkc6LWE5paZ5a6kLee7j+a1jueuoeeQhuWtpumZohzotYTmlpnlrqQt55Sf54mp5oqA5pyv5a2m6ZmiK+i1hOaWmeWupC3lpJbor63lrabpmaLvvIjlrabnlJ/pmIXop4jlrqTvvIkl6LWE5paZ5a6kLeazleWtpumZou+8iOS5puOAgei/h+WIiu+8iR/otYTmlpnlrqQt5pS/566h6Zmi6YC76L6R5Z+65ZywHOi1hOaWmeWupC3mpI3niankv53miqTlrabpmaIJ5Ye654mI56S+KOi1hOaWmeWupC3lm73pmYXlrabpmaLvvIjkuabjgIHov4fliIrvvIko6ams5YWL5oCd5Li75LmJ5a2m6ZmiLe+8iOS5puOAgei/h+WIiu+8iQnmiYDmnInppoYVJwExATIBMwE0ATUBNgE3ATgBOQIxMAIxMQIxMgIxMwIxNAIxNQIxNgIxNwIxOAIxOQIyMAIyMQIyMgIyMwIyNAIyNQIyNgIyNwIyOAIyOQIzMAIzMQIzMgIzMwIzNAIzNQIzNgIzNwIzOAnmiYDmnInppoYUKwMnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnFgECJmQCLA8PFgIfAWVkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUMSW1hZ2VCdXR0b24yBQxJbWFnZUJ1dHRvbjNVY819wM9ps0DF79mssO9oS6AOcQ==");
        data.put("__EVENTVALIDATION", "/wEWfgLM3pjmCQLgnZ70BALSwtXkAgLSwsGJCgKjjKTsAgKd5I/lCgKSi6WLBgKTi6WLBgKQi6WLBgKRi6WLBgL918izBALn/a+ACwLn/a+ACwLn/a+ACwLn/a+ACwKHkLivDgKj9tD3DwLt37y/CQLt37y/CQLNhoi5AgKg5I/lCgLTrdSNCQL4rPjBBwKnururBQLxyoK0AQLY4tPQAgKOpaXzCQKKpbXzCQK9pcHzCQK1pf3zCQK2pfXzCQKPpYHzCQKJpfXzCQKMpbXzCQKMpYHzCQKNpYHzCQKNpZnzCQKNpcHzCQKOpZXyCQKPpbHzCQKPpanzCQKIpcnzCQKJpdHzCQKKpYHzCQKKpanzCQK7pa3zCQK7pcHzCQK0pYnzCQK0pY3zCQK0pa3zCQK0paXzCQK0pfXzCQK0pcnzCQK0pc3zCQK2pd3zCQK2pZHyCQK3pbHzCQKwpaXzCQKwpZHyCQKxpbnzCQKxpf3zCQKjpa3zCQKjpc3zCQKjpcHzCQK9pbnzCQK+pYHzCQK+pa3zCQK/pbHzCQK/pbXzCQK/pd3zCQK/pcHzCQK/pZHyCQK4pZnzCQK4panzCQK5paXzCQKrpbHzCQKkpcHzCQKkpZXyCQLwsfzjBgLxl8uOAwLSy7SPBQL3jKLTDQKM54rGBgLe64HXAgLB36CtCwLgyKrzCALsh7ajDQLj6JzNAQLi6JzNAQLh6JzNAQLg6JzNAQLn6JzNAQLm6JzNAQLl6JzNAQL06JzNAQL76JzNAQLj6NzOAQLj6NDOAQLj6NTOAQLj6OjOAQLj6OzOAQLj6ODOAQLj6OTOAQLj6PjOAQLj6LzNAQLj6LDNAQLi6NzOAQLi6NDOAQLi6NTOAQLi6OjOAQLi6OzOAQLi6ODOAQLi6OTOAQLi6PjOAQLi6LzNAQLi6LDNAQLh6NzOAQLh6NDOAQLh6NTOAQLh6OjOAQLh6OzOAQLh6ODOAQLh6OTOAQLh6PjOAQLh6LzNAQKA0PL0C0qFCFNo6tJHd4xU8W/vWjxuynRQ");
        data.put("TxtIndex", bookName);
        data.put("DropDownList1", "所有");
        data.put("DropDownList2", "馆藏书目库");
        data.put("DropDownList3", "入藏日期");
        data.put("DropDownList4", "前方一致");
        data.put("DropLanguage", "不限");
        data.put("RadioButtonList1", "列表方式");
        data.put("HiddenValue", "");
        data.put("hidtext", "题名");
        data.put("hidValue", "馆藏书目库");
        data.put("DrpHouse", "所有馆");
        data.put("__ASYNCPOST", "true");
        data.put("Button1", "开始检索");
        LibApi.getLibSearchList().searchList("1").flatMap(new Func1<String, Observable<List<SearchBookItem>>>() {
            @Override
            public Observable<List<SearchBookItem>> call(String s) {
                if (s.contains("表名无效")) {
                    return Observable.error(new Throwable("searchagain"));
                } else if (s.contains("没有检索到任何图书！")) {
                    return Observable.error(new Throwable("没有检索到任何图书"));
                } else
                    return Observable.just(LibParse.getSearchList(s));

            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable.getMessage().contains("searchagain")) {

                            return LibApi.getLibSearch().search(data);
                        } else
                            return Observable.error(throwable);

                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);

    }

    @Override
    public void firstSearch(String bookName) {
        subscriber2 = new Subscriber<List<SearchBookItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                String error=throwable.getMessage();
                if (throwable instanceof UnknownHostException) {
                    error = Constant.CLIENT_ERROR;
                } else if (throwable instanceof SocketTimeoutException) {
                    error = Constant.CLIENT_TIMEOUT;
                }

                libSearchview.ShowError(error);
            }

            @Override
            public void onNext(List<SearchBookItem> searchBookItems) {
                searchBookItemList.clear();
                searchBookItemList.addAll(searchBookItems);
                libSearchview.ShowResult(searchBookItemList);
            }
        };
        final Map<String, String> data = new HashMap<>();
        data.put("__EVENTTARGET", "");
        data.put("__EVENTARGUMENT", "");
        data.put("__LASTFOCUS", "");
        data.put("__VIEWSTATE", "/wEPDwUKLTIzODUwODY1Nw9kFgICAw9kFgICBQ9kFgJmD2QWFAIBD2QWBAIBDxYCHglpbm5lcmh0bWwFDOWMl+S6rOmHkeebmGQCAw8PFgIeBFRleHQF1wQ8dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nZGVmYXVsdC5hc3B4Jz48c3Bhbj7pppbpobU8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J2RlZmF1bHQuYXNweCc+PHNwYW4+5Lmm55uu5p+l6K+iPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdNYWdhemluZUNhbnRvU2NhcmNoLmFzcHgnPjxzcGFuPuacn+WIiuevh+WQjTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nUmVzZXJ2ZWRMaXN0LmFzcHgnPjxzcGFuPumihOe6puWIsOmmhjwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nRXhwaXJlZExpc3QuYXNweCc+PHNwYW4+6LaF5pyf5YWs5ZGKPC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdOZXdCb29LU2NhcmNoLmFzcHgnPjxzcGFuPuaWsOS5pumAmuaKpTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nUmVhZGVyTG9naW4uYXNweCc+PHNwYW4+6K+76ICF55m75b2VPC9zcGFuPjwvQT48L3RkPmRkAgMPDxYCHwEFBumHkeebmGRkAgcPZBYEAgIPDxYCHwEFMjxzcGFuPuasoui/juaCqDpHdWVzdCDor7fpgInmi6nkvaDnmoTmk43kvZw8L3NwYW4+ZGQCAw8PFgIeB1Zpc2libGVoZGQCDg9kFgJmDw8WAh8BBe8P54Ot6Zeo5qOA57SiOjxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5bmz5Yeh55qE5LiW55WMJyk7Ij4g5bmz5Yeh55qE5LiW55WMPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn57qi5qW85qKmJyk7Ij4g57qi5qW85qKmPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5Zu05Z+OJyk7Ij4g5Zu05Z+OPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgn5b+D55CG5a2mJyk7Ij4g5b+D55CG5a2mPC9TUEFOPiZuYnNwOyZuYnNwOzxTUEFOIHN0eWxlPSJCT1JERVItQk9UVE9NOiAjNDg5MUJGIDFweCBzb2xpZDsgQkFDS0dST1VORC1DT0xPUjogI0Q4RUZGNTsgQ1VSU09SOiBoYW5kOyBCT1JERVItUklHSFQ6ICM0ODkxQkYgMXB4IHNvbGlkIiBvbmNsaWNrPSJTZXRWYWx1ZSgnU1BTUycpOyI+IFNQU1M8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfnmb7lubTlraTni6wnKTsiPiDnmb7lubTlraTni6w8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfniLHnmoTmlZnogrInKTsiPiDniLHnmoTmlZnogrI8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCflj7LorrAnKTsiPiDlj7LorrA8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfmoqbnmoTop6PmnpAnKTsiPiDmoqbnmoTop6PmnpA8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006ICM0ODkxQkYgMXB4IHNvbGlkOyBCQUNLR1JPVU5ELUNPTE9SOiAjRDhFRkY1OyBDVVJTT1I6IGhhbmQ7IEJPUkRFUi1SSUdIVDogIzQ4OTFCRiAxcHggc29saWQiIG9uY2xpY2s9IlNldFZhbHVlKCfov73po47nrZ3nmoTkuronKTsiPiDov73po47nrZ3nmoTkuro8L1NQQU4+Jm5ic3A7Jm5ic3A7PFNQQU4gc3R5bGU9IkJPUkRFUi1CT1RUT006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5Lmm44CB6L+HJei1hOaWmeWupC3mlofnjK7miYDvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeaVmeiCsuWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5aSW6K+t5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mlL/nrqHpmaLvvIjkuabjgIHov4fliIrvvIki6LWE5paZ5a6kLeiuoeenkemZou+8iOS5puOAgei/h++8iSjpmaLotYTmlpnlrqQt5Zyw55CG5a2m6Zmi77yI5Lmm44CB6L+H77yJIui1hOaWmeWupC3mlbDnu5/pmaLvvIjkuabjgIHov4fvvIkl6LWE5paZ5a6kLeiNr+WtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5L2T6IKy5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mlZnnp5HmiYDvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeWOhuWPsuWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5YyW5bel5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJJei1hOaWmeWupC3mloflrabpmaLvvIjkuabjgIHov4fliIrvvIko6LWE5paZ5a6kLeW/g+eQhuWtpumZou+8iOS5puOAgei/h+WIiu+8iSvotYTmlpnlrqQt55Sf5ZG956eR5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJKOi1hOaWmeWupC3pn7PkuZDlrabpmaLvvIjkuabjgIHov4fliIrvvIk06LWE5paZ5a6kLeaWh+enkeWfuuWcsOilv+awkeS4reW/g++8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt576O5pyv5a2m6Zmi77yI5Lmm44CB6L+H5YiK77yJIui1hOaWmeWupC3nlLXlrZDkv6Hmga/lt6XnqIvlrabpmaIo6LWE5paZ5a6kLeWfueiureWtpumZou+8iOS5puOAgei/h+WIiu+8iSjotYTmlpnlrqQt5paw6K+X56CU56m25omA77yI5Lmm44CB6L+H5YiKKOi1hOaWmeWupC3niannkIblrabpmaLvvIjkuabjgIHov4fliIrvvIku6LWE5paZ5a6kLeS5oeadkeW7uuiuvuWtpumZou+8iOS5puOAgei/h+WIiu+8iSXotYTmlpnlrqQt5Yqo56eR6Zmi77yI5Lmm44CB6L+H5YiK77yJKOi1hOaWmeWupC3nurrnu4flrabpmaLvvIjkuabjgIHov4fliIrvvIkc6LWE5paZ5a6kLee7j+a1jueuoeeQhuWtpumZohzotYTmlpnlrqQt55Sf54mp5oqA5pyv5a2m6ZmiK+i1hOaWmeWupC3lpJbor63lrabpmaLvvIjlrabnlJ/pmIXop4jlrqTvvIkl6LWE5paZ5a6kLeazleWtpumZou+8iOS5puOAgei/h+WIiu+8iR/otYTmlpnlrqQt5pS/566h6Zmi6YC76L6R5Z+65ZywHOi1hOaWmeWupC3mpI3niankv53miqTlrabpmaIJ5Ye654mI56S+KOi1hOaWmeWupC3lm73pmYXlrabpmaLvvIjkuabjgIHov4fliIrvvIko6ams5YWL5oCd5Li75LmJ5a2m6ZmiLe+8iOS5puOAgei/h+WIiu+8iQnmiYDmnInppoYVJwExATIBMwE0ATUBNgE3ATgBOQIxMAIxMQIxMgIxMwIxNAIxNQIxNgIxNwIxOAIxOQIyMAIyMQIyMgIyMwIyNAIyNQIyNgIyNwIyOAIyOQIzMAIzMQIzMgIzMwIzNAIzNQIzNgIzNwIzOAnmiYDmnInppoYUKwMnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnFgECJmQCLA8PFgIfAWVkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUMSW1hZ2VCdXR0b24yBQxJbWFnZUJ1dHRvbjNVY819wM9ps0DF79mssO9oS6AOcQ==");
        data.put("__EVENTVALIDATION", "/wEWfgLM3pjmCQLgnZ70BALSwtXkAgLSwsGJCgKjjKTsAgKd5I/lCgKSi6WLBgKTi6WLBgKQi6WLBgKRi6WLBgL918izBALn/a+ACwLn/a+ACwLn/a+ACwLn/a+ACwKHkLivDgKj9tD3DwLt37y/CQLt37y/CQLNhoi5AgKg5I/lCgLTrdSNCQL4rPjBBwKnururBQLxyoK0AQLY4tPQAgKOpaXzCQKKpbXzCQK9pcHzCQK1pf3zCQK2pfXzCQKPpYHzCQKJpfXzCQKMpbXzCQKMpYHzCQKNpYHzCQKNpZnzCQKNpcHzCQKOpZXyCQKPpbHzCQKPpanzCQKIpcnzCQKJpdHzCQKKpYHzCQKKpanzCQK7pa3zCQK7pcHzCQK0pYnzCQK0pY3zCQK0pa3zCQK0paXzCQK0pfXzCQK0pcnzCQK0pc3zCQK2pd3zCQK2pZHyCQK3pbHzCQKwpaXzCQKwpZHyCQKxpbnzCQKxpf3zCQKjpa3zCQKjpc3zCQKjpcHzCQK9pbnzCQK+pYHzCQK+pa3zCQK/pbHzCQK/pbXzCQK/pd3zCQK/pcHzCQK/pZHyCQK4pZnzCQK4panzCQK5paXzCQKrpbHzCQKkpcHzCQKkpZXyCQLwsfzjBgLxl8uOAwLSy7SPBQL3jKLTDQKM54rGBgLe64HXAgLB36CtCwLgyKrzCALsh7ajDQLj6JzNAQLi6JzNAQLh6JzNAQLg6JzNAQLn6JzNAQLm6JzNAQLl6JzNAQL06JzNAQL76JzNAQLj6NzOAQLj6NDOAQLj6NTOAQLj6OjOAQLj6OzOAQLj6ODOAQLj6OTOAQLj6PjOAQLj6LzNAQLj6LDNAQLi6NzOAQLi6NDOAQLi6NTOAQLi6OjOAQLi6OzOAQLi6ODOAQLi6OTOAQLi6PjOAQLi6LzNAQLi6LDNAQLh6NzOAQLh6NDOAQLh6NTOAQLh6OjOAQLh6OzOAQLh6ODOAQLh6OTOAQLh6PjOAQLh6LzNAQKA0PL0C0qFCFNo6tJHd4xU8W/vWjxuynRQ");
        data.put("TxtIndex", bookName);
        data.put("DropDownList1", "所有");
        data.put("DropDownList2", "馆藏书目库");
        data.put("DropDownList3", "入藏日期");
        data.put("DropDownList4", "前方一致");
        data.put("DropLanguage", "不限");
        data.put("RadioButtonList1", "列表方式");
        data.put("HiddenValue", "");
        data.put("hidtext", "题名");
        data.put("hidValue", "馆藏书目库");
        data.put("DrpHouse", "所有馆");
        data.put("__ASYNCPOST", "true");
        data.put("Button1", "开始检索");
        LibApi.getLibSearch().search(data).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return LibApi.getLibSearchList().searchList("0");
            }
        }).flatMap(new Func1<String, Observable<List<SearchBookItem>>>() {
            @Override
            public Observable<List<SearchBookItem>> call(String s) {
                if (s.contains("没有检索到任何图书")) {
                    return Observable.error(new Throwable("没有检索到任何图书"));
                } else
                    return Observable.just(LibParse.getSearchList(s));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber2);
    }

    @Override
    public void cancelSearch() {
        if (subscriber1!=null&&subscriber1.isUnsubscribed()) {
            subscriber1.unsubscribe();
        }
        if (subscriber2!=null&&subscriber2.isUnsubscribed()) {
            subscriber2.unsubscribe();
        }
    }

}
