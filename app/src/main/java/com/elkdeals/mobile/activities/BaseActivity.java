package com.elkdeals.mobile.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.LocaleHelper;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.custom.NotificationBadger;
import com.elkdeals.mobile.databinding.ActivityBaseBinding;
import com.elkdeals.mobile.dialogFragments.MessageDialog;
import com.elkdeals.mobile.interfaces.OnConnectionStatusChanged;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.recievers.ConnectivityReceiver;
import com.elkdeals.mobile.recievers.NotesReciever;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.HomeActivity;
import com.elkdeals.mobile.ui.Youtube;
import com.elkdeals.mobile.ui.account.Bills;
import com.elkdeals.mobile.ui.account.FAQ;
import com.elkdeals.mobile.ui.account.MyAccount;
import com.elkdeals.mobile.ui.account.Notifications;
import com.elkdeals.mobile.ui.account.ShippingAddresses;
import com.elkdeals.mobile.ui.account.Wallet;
import com.elkdeals.mobile.ui.auctions.Auctions;
import com.elkdeals.mobile.ui.auctions.AuctionsActivity;
import com.elkdeals.mobile.ui.auctions.AuctionsCart;
import com.elkdeals.mobile.ui.auctions.AuctionsCategories;
import com.elkdeals.mobile.ui.auctions.PreviousAuctions;
import com.elkdeals.mobile.ui.requests.CustomersRequests;
import com.elkdeals.mobile.ui.requests.Offers;
import com.elkdeals.mobile.ui.requests.RequestActivity;
import com.elkdeals.mobile.ui.requests.Requests;
import com.elkdeals.mobile.ui.splash.Splash;
import com.elkdeals.mobile.ui.store.CategoriesStore;
import com.elkdeals.mobile.ui.store.My_Orders;
import com.elkdeals.mobile.ui.store.StoreActivity;
import com.elkdeals.mobile.ui.store.StoreCart;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.elkdeals.mobile.Utils.Constants.CONTRACT;
import static com.elkdeals.mobile.Utils.Constants.CONTRACT_URL;
import static com.elkdeals.mobile.Utils.Constants.DATA;
import static com.elkdeals.mobile.Utils.Constants.TERMS;
import static com.elkdeals.mobile.Utils.Constants.TERMS_URL;
import static com.elkdeals.mobile.Utils.Utils.fetchAccentColor;

public abstract class BaseActivity extends AppCompatActivity
        implements DrawerLayout.DrawerListener, FragmentManager.OnBackStackChangedListener, OnConnectionStatusChanged {
    public static int countNotification = 0;
    public static int countNotificationPrev = -1, countCartPrev = -1;
    protected boolean auctionsCart = true;
    public BaseActivity activity;
    final String className = getClass().getName();
    public boolean showDrawer = true;
    public boolean showToolBarUpOnLy = false;
    public MenuAdapter adapter;
    public MessageDialog messageDialog;
    Toast toast;
    public static BaseActivity instance;
    BroadcastReceiver broadcastReceiver;
    @Nullable
    @BindView(R.id.nav_view)
    public NavigationView navigationView;
    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @Nullable
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.networkLinearLayout)
    View networkLinearLayout;
    BaseFragment fragment;
    private ActivityBaseBinding binding;

    public BaseActivity()
    {
        activity = this;
        instance=this;
        ConnectivityReceiver.observeNetworkStatus(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NotesReciever.recieveNotes(activity, intent);
            }
        };
    }/*
    public void setUpThemeActionBarr(String fragmentType) {

        getSupportActionBar().setTitle("");
        switch (fragmentType) {
            case AuctionsCart.TAG:
            case AuctionsCategories.TAG:
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_auctions));
                navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_auctions));
                break;
            default:
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_requests));
                navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));
                break;
        }
    }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

//        if (showToolBarUpOnLy)
//            getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        if (LocaleHelper.getLanguage(this).toLowerCase().equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (!className.equals(Splash.class.getName()))
            binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
    }
    @Override
    public void onBackStackChanged() {
        if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container)) instanceof BaseFragment)
            if (adapter != null)
                adapter.setSelected(((BaseFragment) (getSupportFragmentManager().findFragmentById(R.id.fragment_container))).getTAG());
        onCurrentFragmentChanged();
        Object fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof BaseFragment){
          //  Log.e("onBackStackChanged",((BaseFragment) fragment).getTAG());
            ((BaseFragment) fragment).onResume_();
        }

    }

    @Override
    protected void onResume() {
        instance=this;
        super.onResume();
        if(this instanceof HomeActivity&&adapter!=null)
                adapter.setSelected(HomeActivity.TAG);
        else if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container)) instanceof BaseFragment)
            if (adapter != null)
                adapter.setSelected(((BaseFragment) (getSupportFragmentManager().findFragmentById(R.id.fragment_container))).getTAG());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(broadcastReceiver);
        }
        catch (Exception ignored){

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    protected <T extends ViewDataBinding> T putContentView(@LayoutRes int resId) {
        T added = DataBindingUtil.inflate(getLayoutInflater(), resId, binding.activityContainer, true);
        ButterKnife.bind(this);
        if (!className.equals(Splash.class.getName()))
            initDrawer();
        initViews();
        NotesReciever.callNotifications(broadcastReceiver, activity);
        return added;
    }

    public Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (showToolBarUpOnLy && getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ImageView img = findViewById(R.id.toolbar_logo);
            if (img != null)
                img.setVisibility(View.INVISIBLE);
        }
        return toolbar;
    }

    public void initDrawer() {
        Toolbar toolbar = initToolBar();
        if (drawer == null)
            return;
        if (showDrawer) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                    toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        } else
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setDrawerListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MenuAdapter(getItems(),activity, new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                onNavigationItemSelected((String) data);
            }
        });
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_from_right);
            //recyclerView.setLayoutAnimation(animation);
        }
        recyclerView.setAdapter(adapter);
        divider.setBackground(new ColorDrawable(fetchAccentColor(activity)));
        //recyclerView.setItemAnimator(new FlexibleItemAnimator());
        //recyclerView.scheduleLayoutAnimation();


    }
    public ArrayList<MenuAdapter.MenuItem> getAuctionsMenuItems() {
        ArrayList<MenuAdapter.MenuItem> items = new ArrayList<>();
        items.add(new MenuAdapter.MenuItem(R.string.home, HomeActivity.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.bills, Bills.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.wallet, Wallet.TAG));
        items.add(new MenuAdapter.MenuItem("مزاداتى", CurrentAuctions.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.prev_auctions, PreviousAuctions.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.terms, TERMS));
        items.add(new MenuAdapter.MenuItem(R.string.contract, CONTRACT));
        items.add(new MenuAdapter.MenuItem(R.string.faq, FAQ.TAG));
        return items;
    }
    public ArrayList<MenuAdapter.MenuItem> getStoreMenuItems() {
        ArrayList<MenuAdapter.MenuItem> items = new ArrayList<>();
        items.add(new MenuAdapter.MenuItem(R.string.home, HomeActivity.TAG));
        //items.add(new MenuItem(R.string.cart, Cart.TAG));
        items.add(new MenuAdapter.MenuItem("طلبات المتجر", My_Orders.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.addresses, ShippingAddresses.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.terms, TERMS));
        items.add(new MenuAdapter.MenuItem(R.string.contract, CONTRACT));
        items.add(new MenuAdapter.MenuItem(R.string.faq, FAQ.TAG));
        return items;
    }
    public ArrayList<MenuAdapter.MenuItem> getRequestsMenuItems() {
        ArrayList<MenuAdapter.MenuItem> items = new ArrayList<>();
        items.add(new MenuAdapter.MenuItem(R.string.home, HomeActivity.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.bills, Bills.TAG));
        //items.add(new MenuItem(R.string.cart, Cart.TAG));
        items.add(new MenuAdapter.MenuItem("طلبات المتجر", My_Orders.TAG));
        if (RepositorySidalitac.getUserInfo() != null && RepositorySidalitac.getUserInfo().getData() != null
                && RepositorySidalitac.getUserInfo().getData().size() > 0 &&
                RepositorySidalitac.getUserInfo().getData().get(0).getIsSeller() == 1)
            items.add(new MenuAdapter.MenuItem(R.string.customers_requests, CustomersRequests.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.addresses, ShippingAddresses.TAG));

        items.add(new MenuAdapter.MenuItem(R.string.terms, TERMS));
        items.add(new MenuAdapter.MenuItem(R.string.contract, CONTRACT));
        items.add(new MenuAdapter.MenuItem(R.string.faq, FAQ.TAG));
        return items;
    }

    public ArrayList<MenuAdapter.MenuItem> getDefaultMenuItems() {
        ArrayList<MenuAdapter.MenuItem> items = new ArrayList<>();
        items.add(new MenuAdapter.MenuItem(R.string.home, HomeActivity.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.bills, Bills.TAG));
        //items.add(new MenuItem(R.string.cart, Cart.TAG));
        items.add(new MenuAdapter.MenuItem("طلبات المتجر", My_Orders.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.wallet, Wallet.TAG));
        items.add(new MenuAdapter.MenuItem("مزاداتى", CurrentAuctions.TAG));
        if (RepositorySidalitac.getUserInfo() != null && RepositorySidalitac.getUserInfo().getData() != null
                && RepositorySidalitac.getUserInfo().getData().size() > 0 &&
                RepositorySidalitac.getUserInfo().getData().get(0).getIsSeller() == 1)
            items.add(new MenuAdapter.MenuItem(R.string.customers_requests, CustomersRequests.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.prev_auctions, PreviousAuctions.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.addresses, ShippingAddresses.TAG));
        items.add(new MenuAdapter.MenuItem(R.string.terms, TERMS));
        items.add(new MenuAdapter.MenuItem(R.string.contract, CONTRACT));
        items.add(new MenuAdapter.MenuItem(R.string.faq, FAQ.TAG));
        return items;
    }

    public abstract ArrayList<MenuAdapter.MenuItem> getItems();

    public void showToast(@StringRes int res) {
        String message = getString(res);
        runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            int length = Toast.LENGTH_SHORT;
            if (message.length() > 40)
                length = Toast.LENGTH_LONG;
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }

    public void showToast(String message) {
        runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            int length = Toast.LENGTH_SHORT;
            if (message.length() > 40)
                length = Toast.LENGTH_LONG;
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            removeTopFragment();
        else finish();
    }

    public void removeTopFragment() {
        getSupportFragmentManager().popBackStack();
    }

    public void onCurrentFragmentChanged() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (showToolBarUpOnLy)
            return true;
        getMenuInflater().inflate(R.menu.main, menu);

        /*//Cart

        MenuItem cartItem = menu.findItem(R.id.ic_cart);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageCart = (ImageView) inflater.inflate(R.layout.menu_cart, null);
        Drawable itemIcon = cartItem.getIcon().getCurrent();
        imageCart.setImageDrawable(itemIcon);
        cartItem.setActionView(imageCart);

        //Notification

        MenuItem notification = menu.findItem(R.id.ic_notification);
        ImageView imageNotification = (ImageView) inflater.inflate(R.layout.menu_notification, null);
        Drawable itemIconNotification = notification.getIcon().getCurrent();
        imageNotification.setImageDrawable(itemIconNotification);
        notification.setActionView(imageNotification);
        //Utils.tintMenuIcon(BaseActivity.this, cartItem, R.color.white);
*/
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void setCountNotification(int countNotification) {
        BaseActivity.countNotification = countNotification;
        invalidateOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (showToolBarUpOnLy)
            return super.onPrepareOptionsMenu(menu);
        MenuItem notification = menu.findItem(R.id.ic_notification);
        MenuItem cartItem = menu.findItem(R.id.ic_cart);
        int countCart = 0;
        if (auctionsCart) {
            List cartItems = StoreCartManager.getInstance().getAuctions();
            if (cartItems == null || cartItems.size() == 0)
                countCart = 0;
            else countCart = cartItems.size();
        } else {
            ArrayList<CartProduct> cartItems = StoreCartManager.getInstance().getCartItems();
            if (cartItems != null)
                for (CartProduct cartProduct : cartItems)
                    countCart = countCart + cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity();
        }

        //Cart Cart
        if (countCart > 0) {

            LayerDrawable icon = null;
            Drawable drawable = cartItem.getIcon();

            if (drawable instanceof DrawableWrapper) {
                drawable = ((DrawableWrapper) drawable).getWrappedDrawable();
            } else if (drawable instanceof WrappedDrawable) {
                drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
            }


            if (drawable instanceof LayerDrawable) {
                icon = (LayerDrawable) drawable;
            } else if (drawable instanceof DrawableWrapper) {
                DrawableWrapper wrapper = (DrawableWrapper) drawable;
                if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                    icon = (LayerDrawable) wrapper.getWrappedDrawable();
                }
            }
            if (icon != null)
                NotificationBadger.setBadgeCount(this, icon, String.valueOf(countCart));


        } else {
            // Set the Icon for Empty Cart
            cartItem.setIcon(R.drawable.toggle_ic_cart);
        }
        //Notification Count
        if (countNotification > 0) {
            LayerDrawable icon = null;
            Drawable drawable = notification.getIcon();

            if (drawable instanceof DrawableWrapper) {
                drawable = ((DrawableWrapper) drawable).getWrappedDrawable();
            } else if (drawable instanceof WrappedDrawable) {
                drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
            }


            if (drawable instanceof LayerDrawable) {
                icon = (LayerDrawable) drawable;
            } else if (drawable instanceof DrawableWrapper) {
                DrawableWrapper wrapper = (DrawableWrapper) drawable;
                if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                    icon = (LayerDrawable) wrapper.getWrappedDrawable();
                }
            }
            if (icon != null)
                NotificationBadger.setBadgeCount(this, icon, String.valueOf(countNotification));


        } else {
            // Set the Icon for Empty Cart
            notification.setIcon(R.drawable.toggle_ic_notification);
        }
        super.onPrepareOptionsMenu(menu);
        if (countNotification != countNotificationPrev)
            Utils.animateCartMenuIcon(notification);
        if (countCart != countCartPrev)
            Utils.animateCartMenuIcon(cartItem);
        countNotificationPrev = countNotification;
        countCartPrev = countCart;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.ic_notification:
                onNavigationItemSelected(Notifications.TAG);
                break;
            case R.id.ic_cart:
                if (auctionsCart)
                    onNavigationItemSelected(AuctionsCart.TAG);
                else onNavigationItemSelected(StoreCart.TAG);
                break;
        }
        fragment = null;
        return true;
    }

    public BaseFragment getFragment(String fragment){
        BaseFragment initialFragment = null;
        if (TextUtils.isEmpty(fragment))
            return initialFragment;
        switch (fragment){

            case AuctionsCategories.TAG:
                initialFragment = new AuctionsCategories();
                break;
            case PreviousAuctions.TAG:
                initialFragment = AuctionsCategories.getPreviousAuctionsInstance();
                break;
            case Bills.TAG:
                initialFragment = new Bills();
                break;
            case Wallet.TAG:
                initialFragment = new Wallet();
                break;
            case FAQ.TAG:
                initialFragment = new FAQ();
                break;
            case AuctionsCart.TAG:
                initialFragment = new AuctionsCart();
                break;
            case MyAccount.TAG:
                initialFragment = new MyAccount();
                break;
            case Notifications.TAG:
                initialFragment = new Notifications();
                break;
            case Auctions.TAG:
                initialFragment = new Auctions();
                break;
            case CategoriesStore.TAG:
                initialFragment = new CategoriesStore();
                break;
            case Requests.TAG:
                initialFragment = new Requests();
                break;
            case CustomersRequests.TAG:
                initialFragment = new CustomersRequests();
                break;
            case StoreCart.TAG:
                initialFragment = new StoreCart();
                break;
            case My_Orders.TAG:
                initialFragment = new My_Orders();
                break;
            case ShippingAddresses.TAG:
                if(RepositorySidalitac.getUserInfo() == null||RepositorySidalitac.getUserInfo().getData()==null)
                {
                    showToast(R.string.store_blocked);
                }
                else initialFragment = new ShippingAddresses();
                break;
            case Offers.TAG:
                initialFragment = new Offers();
                break;
            default:
                initialFragment = null;
                break;
        }
        return initialFragment;
    }
    public   void onNavigationItemSelected(String item) {
        BaseFragment fragment = null;
        Class activity = null;
        switch (item) {
            case FAQ.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case Notifications.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case HomeActivity.TAG:
                home();
                return;
             case StoreCart.TAG:
                activity = StoreActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case AuctionsCart.TAG:
                activity = AuctionsActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case MyAccount.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case Bills.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case Wallet.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case PreviousAuctions.TAG:
                activity = AuctionsActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case Offers.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case CustomersRequests.TAG:
                activity = RequestActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case My_Orders.TAG:
                activity = StoreActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case ShippingAddresses.TAG:
                activity = StoreActivity.class;// args.putString(DATA, FAQ.TAG);
                break;
            case CurrentAuctions.TAG:

                {
                Intent intent = new Intent(this, CurrentAuctions.class);
                intent.putExtra("WHO", "PINCODE");
                startActivity(intent);
                    return;
            }
            case TERMS:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_URL));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Log.e(TERMS, e.getMessage());
                }
                return;
            case CONTRACT:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(CONTRACT_URL));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Log.e(CONTRACT, e.getMessage());
                }
                return;
        }
        if (fragment!=null)
            addPage(fragment);
        if (activity!=null)
        {
            Bundle args = new Bundle();
            args.putString(DATA, item);
            startActivity(activity,args);
        }
        //startActivity(MainActivity.class, args);
    }

    public void closeDrawer() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void startActivity(Class activityClass) {
        Intent intent = new Intent(activity, activityClass);
        startActivity(intent);
    }

    public void startActivity(Class activityClass, Bundle data) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(DATA, data);
        startActivity(intent);
    }


    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        if (navigationView != null) {
            navigationView.bringToFront();
            navigationView.requestLayout();
        }
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        if (fragment != null) {
            fragment = null;
        }
    }

    @Optional
    @OnClick(R.id.account_info)
    public void showAccountInfo() {
        onNavigationItemSelected(MyAccount.TAG);
    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /////////////////////////////////////////////////
    //message methods
    /////////////////////////////////////////////////

    public void showProgressBar(String message) {
        try {
            if (messageDialog != null && !messageDialog.isHidden())
                messageDialog.dismiss();
        } catch (Exception ignored) {
        }
        messageDialog = MessageDialog.createProgressDialog(message);
        messageDialog.show(getSupportFragmentManager(), MessageDialog.class.getName());
    }
public void showProgressBar() {
    String message="جار التحميل...";
        try {
            if (messageDialog != null && !messageDialog.isHidden())
                messageDialog.dismiss();
        } catch (Exception ignored) {
        }
        messageDialog = MessageDialog.createProgressDialog(message);
        messageDialog.show(getSupportFragmentManager(), MessageDialog.class.getName());
    }

    public void hideDialog() {
        runOnUiThread(() -> {
            try {
                if (messageDialog != null && !messageDialog.isHidden())
                    messageDialog.dismiss();
                messageDialog = null;
            } catch (Exception ignored) {
            }
        });

    }
    @Optional
    @OnClick(R.id.toolbar_logo)
    public void home() {
        if (this instanceof HomeActivity)
            return;
        Class c =  HomeActivity.class;
        if(Repository.getUserInfo() == null)
            c = Splash.class;
        startActivity(c);
        finishAffinity();
    }
    public void showStore() {
        Bundle args = new Bundle();
        args.putString(DATA, CategoriesStore.TAG);
        activity.startActivity(StoreActivity.class);
    }

    public void showMessageDialog(final String message) {
        if(TextUtils.isEmpty(message))return;
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            showDialog();
        });
    }
    public void showMessageDialog(@StringRes final int message) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            showDialog();
        });
    }

    public void showMessageDialog(final String message, final View.OnClickListener onOkclick) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    onOkclick.onClick(v);
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            messageDialog.setCancelable(false);
            showDialog();
        });
    }

    public void showMessageDialog(final String title, final String message) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            messageDialog.setCancelable(true);
            showDialog();
        });
    }
    public void showMessageDialog(final String title, final String message, final View.OnClickListener onOkclick) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    onOkclick.onClick(v);
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            messageDialog.setCancelable(true);
            showDialog();
        });
    }

    private void showDialog() {
        try {
            messageDialog.show(getSupportFragmentManager(), "tag");
        } catch (Exception ignored) {
        }
    }
    public void showMessageDialog(final String title, final String message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                    onCancelclick.onClick(v);
                }
            });
            messageDialog.setCancelable(true);
            showDialog();
        });
    }
    public void showMessageDialog(final String title, final String message, String ok ,String cancel,final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick) {
        runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                    onCancelclick.onClick(v);
                }
            });
            messageDialog.setOk(ok);
            messageDialog.setCancel(cancel);
            messageDialog.setCancelable(false);
            showDialog();
        });
    }
    ///////////////////////////////////////
    public void addPage(BaseFragment fragment,BaseFragment defaultf){
        if (fragment == null)
            addPage(defaultf);
        else addPage(fragment);
    }
    public void addPage(BaseFragment fragment) {
        if(fragment==null)
            return;
        if(findViewById(R.id.fragment_container)==null){
            onNavigationItemSelected(HomeActivity.TAG);
            return;
        }
        if(!className.equalsIgnoreCase(Splash.class.getName())&&
                 Repository.getUserInfo()==null)
        {
            startActivity(Splash.class);
            return;
        }
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_left, 0, 0, R.anim.slide_out_left
                    )
                    .show(fragment)
                    .addToBackStack(fragment.getTAG()).commitAllowingStateLoss();
            return;
        }
        /*if (getSupportFragmentManager().findFragmentByTag(fragment.getTAG()) != null) {
            etSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(fragment.getTAG())).commit();
            return;
        }*/
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left, 0, 0, R.anim.slide_out_left
                )
                .add(R.id.fragment_container, fragment, fragment.getTAG())
                .addToBackStack(fragment.getTAG()).commitAllowingStateLoss();
    }
    public boolean playYoutubeVideo(Object videoID) {
        return playYoutubeVideo(videoID,false);
        }
    public boolean playYoutubeVideo(Object videoID,boolean closeOnFinish) {
        String mVideoID = null;
        try {
            if (videoID!=null)
            mVideoID = String.valueOf(videoID);
        } catch (Exception ex) {
            activity.showToast(R.string.no_video_found);
            return false;
        }
        if (!TextUtils.isEmpty(mVideoID)) {
            addPage(Youtube.createInstance(mVideoID,closeOnFinish));
        } else{
            activity.showToast(R.string.no_video_found);
            return false;
        }
        return true;
    }    ////////////////////////////////////////////////

    @CallSuper
    public void initViews(){

        //ConnectivityReceiver.getInstance().onReceive(this, getIntent());
    }

    //abstract methods
    ////////////////////////////////////////////////

    public abstract void exitApp(boolean shouldEraseAndLogin);

    @Override
    public void OnConnectionStatusChanged(boolean networkStatus) {
        if(networkLinearLayout!=null)
            networkLinearLayout.setVisibility(networkStatus?View.GONE:View.VISIBLE);
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof BaseFragment)
            ((BaseFragment)(getSupportFragmentManager().findFragmentById(R.id.fragment_container))).OnConnectionStatusChanged(networkStatus);
        if(!networkStatus)
            try{
                activity.showMessageDialog("الرجاء التحقق من اتصال الانترنت لديك!");
            }catch (Exception e){

            }
    }
    // abstract void showFragment(BaseFragment fragment);
}
