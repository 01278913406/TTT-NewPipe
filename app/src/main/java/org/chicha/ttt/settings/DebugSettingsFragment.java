package org.chicha.ttt.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;

import org.chicha.ttt.R;
import org.chicha.ttt.error.ErrorInfo;
import org.chicha.ttt.error.ErrorUtil;
import org.chicha.ttt.error.UserAction;
import org.chicha.ttt.local.feed.notifications.NotificationWorker;
import org.chicha.ttt.util.PicassoHelper;

import java.util.Optional;

public class DebugSettingsFragment extends BasePreferenceFragment {
    private static final String DUMMY = "Dummy";

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        addPreferencesFromResourceRegistry();

        final Preference allowHeapDumpingPreference =
                findPreference(getString(R.string.allow_heap_dumping_key));
        final Preference showMemoryLeaksPreference =
                findPreference(getString(R.string.show_memory_leaks_key));
        final Preference showImageIndicatorsPreference =
                findPreference(getString(R.string.show_image_indicators_key));
        final Preference checkNewStreamsPreference =
                findPreference(getString(R.string.check_new_streams_key));
        final Preference crashTheAppPreference =
                findPreference(getString(R.string.crash_the_app_key));
        final Preference showErrorSnackbarPreference =
                findPreference(getString(R.string.show_error_snackbar_key));
        final Preference createErrorNotificationPreference =
                findPreference(getString(R.string.create_error_notification_key));

        assert allowHeapDumpingPreference != null;
        assert showMemoryLeaksPreference != null;
        assert showImageIndicatorsPreference != null;
        assert checkNewStreamsPreference != null;
        assert crashTheAppPreference != null;
        assert showErrorSnackbarPreference != null;
        assert createErrorNotificationPreference != null;

        final Optional<DebugSettingsBVDLeakCanaryAPI> optBVLeakCanary = getBVDLeakCanary();

        allowHeapDumpingPreference.setEnabled(optBVLeakCanary.isPresent());
        showMemoryLeaksPreference.setEnabled(optBVLeakCanary.isPresent());

        if (optBVLeakCanary.isPresent()) {
            final DebugSettingsBVDLeakCanaryAPI pdLeakCanary = optBVLeakCanary.get();

            showMemoryLeaksPreference.setOnPreferenceClickListener(preference -> {
                startActivity(pdLeakCanary.getNewLeakDisplayActivityIntent());
                return true;
            });
        } else {
            allowHeapDumpingPreference.setSummary(R.string.leak_canary_not_available);
            showMemoryLeaksPreference.setSummary(R.string.leak_canary_not_available);
        }

        showImageIndicatorsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            PicassoHelper.setIndicatorsEnabled((Boolean) newValue);
            return true;
        });

        checkNewStreamsPreference.setOnPreferenceClickListener(preference -> {
            NotificationWorker.runNow(preference.getContext());
            return true;
        });

        crashTheAppPreference.setOnPreferenceClickListener(preference -> {
            throw new RuntimeException(DUMMY);
        });

        showErrorSnackbarPreference.setOnPreferenceClickListener(preference -> {
            ErrorUtil.showUiErrorSnackbar(DebugSettingsFragment.this,
                    DUMMY, new RuntimeException(DUMMY));
            return true;
        });

        createErrorNotificationPreference.setOnPreferenceClickListener(preference -> {
            ErrorUtil.createNotification(requireContext(),
                    new ErrorInfo(new RuntimeException(DUMMY), UserAction.UI_ERROR, DUMMY));
            return true;
        });
    }

    /**
     * Tries to find the {@link DebugSettingsBVDLeakCanaryAPI#IMPL_CLASS} and loads it if available.
     * @return An {@link Optional} which is empty if the implementation class couldn't be loaded.
     */
    private Optional<DebugSettingsBVDLeakCanaryAPI> getBVDLeakCanary() {
        try {
            // Try to find the implementation of the LeakCanary API
            return Optional.of((DebugSettingsBVDLeakCanaryAPI)
                    Class.forName(DebugSettingsBVDLeakCanaryAPI.IMPL_CLASS)
                            .getDeclaredConstructor()
                            .newInstance());
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Build variant dependent (BVD) leak canary API for this fragment.
     * Why is LeakCanary not used directly? Because it can't be assured
     */
    public interface DebugSettingsBVDLeakCanaryAPI {
        String IMPL_CLASS =
                "org.chicha.ttt.settings.DebugSettingsBVDLeakCanary";

        Intent getNewLeakDisplayActivityIntent();
    }
}
