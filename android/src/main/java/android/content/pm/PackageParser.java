package android.content.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;

import java.io.PrintWriter;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJJ on 2017/8/28.
 * all the code copy from android sdk  source code,omit  unnecessary part
 *
 * @author CJJ
 */
//复制出Android sdk 同名类的大致结构即可，用到的方法拷贝出来，没有使用的不需要拷贝
public class PackageParser {


    static class ParsePackageItemArgs {
        final Package owner;
        final String[] outError;
        final int nameRes;
        final int labelRes;
        final int iconRes;
        final int roundIconRes;
        final int logoRes;
        final int bannerRes;

        String tag;
        TypedArray sa;

        ParsePackageItemArgs(Package _owner, String[] _outError,
                             int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes,
                             int _bannerRes) {
            owner = _owner;
            outError = _outError;
            nameRes = _nameRes;
            labelRes = _labelRes;
            iconRes = _iconRes;
            logoRes = _logoRes;
            bannerRes = _bannerRes;
            roundIconRes = _roundIconRes;
        }
    }

    static class ParseComponentArgs extends ParsePackageItemArgs {
        final String[] sepProcesses;
        final int processRes;
        final int descriptionRes;
        final int enabledRes;
        int flags;

        ParseComponentArgs(Package _owner, String[] _outError,
                           int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes,
                           int _bannerRes,
                           String[] _sepProcesses, int _processRes,
                           int _descriptionRes, int _enabledRes) {
            super(_owner, _outError, _nameRes, _labelRes, _iconRes, _roundIconRes, _logoRes,
                    _bannerRes);
            sepProcesses = _sepProcesses;
            processRes = _processRes;
            descriptionRes = _descriptionRes;
            enabledRes = _enabledRes;
        }
    }

    public static class PackageLite {
        public final String packageName;
        public final int versionCode;
        public final int installLocation;
        public final VerifierInfo[] verifiers;

        /**
         * Names of any split APKs, ordered by parsed splitName
         */
        public final String[] splitNames;

        /**
         * Path where this package was found on disk. For monolithic packages
         * this is path to single base APK file; for cluster packages this is
         * path to the cluster directory.
         */
        public final String codePath;

        /**
         * Path of base APK
         */
        public final String baseCodePath;
        /**
         * Paths of any split APKs, ordered by parsed splitName
         */
        public final String[] splitCodePaths;

        /**
         * Revision code of base APK
         */
        public final int baseRevisionCode;
        /**
         * Revision codes of any split APKs, ordered by parsed splitName
         */
        public final int[] splitRevisionCodes;

        public final boolean coreApp;
        public final boolean multiArch;
        public final boolean use32bitAbi;
        public final boolean extractNativeLibs;

        public PackageLite(String codePath, ApkLite baseApk, String[] splitNames,
                           String[] splitCodePaths, int[] splitRevisionCodes) {
            this.packageName = baseApk.packageName;
            this.versionCode = baseApk.versionCode;
            this.installLocation = baseApk.installLocation;
            this.verifiers = baseApk.verifiers;
            this.splitNames = splitNames;
            this.codePath = codePath;
            this.baseCodePath = baseApk.codePath;
            this.splitCodePaths = splitCodePaths;
            this.baseRevisionCode = baseApk.revisionCode;
            this.splitRevisionCodes = splitRevisionCodes;
            this.coreApp = baseApk.coreApp;
            this.multiArch = baseApk.multiArch;
            this.use32bitAbi = baseApk.use32bitAbi;
            this.extractNativeLibs = baseApk.extractNativeLibs;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            return paths;
        }
    }

    public static class ApkLite {
        public final String codePath;
        public final String packageName;
        public final String splitName;
        public final int versionCode;
        public final int revisionCode;
        public final int installLocation;
        public final VerifierInfo[] verifiers;
        public final Signature[] signatures;
        public final Certificate[][] certificates;
        public final boolean coreApp;
        public final boolean multiArch;
        public final boolean use32bitAbi;
        public final boolean extractNativeLibs;

        public ApkLite(String codePath, String packageName, String splitName, int versionCode,
                       int revisionCode, int installLocation, List<VerifierInfo> verifiers,
                       Signature[] signatures, Certificate[][] certificates, boolean coreApp,
                       boolean multiArch, boolean use32bitAbi, boolean extractNativeLibs) {
            this.codePath = codePath;
            this.packageName = packageName;
            this.splitName = splitName;
            this.versionCode = versionCode;
            this.revisionCode = revisionCode;
            this.installLocation = installLocation;
            this.verifiers = verifiers.toArray(new VerifierInfo[verifiers.size()]);
            this.signatures = signatures;
            this.certificates = certificates;
            this.coreApp = coreApp;
            this.multiArch = multiArch;
            this.use32bitAbi = use32bitAbi;
            this.extractNativeLibs = extractNativeLibs;
        }
    }

    public static class NewPermissionInfo {
        public final String name;
        public final int sdkVersion;
        public final int fileVersion;

        public NewPermissionInfo(String name, int sdkVersion, int fileVersion) {
            this.name = name;
            this.sdkVersion = sdkVersion;
            this.fileVersion = fileVersion;
        }

        public static class SplitPermissionInfo {
            public final String rootPerm;
            public final String[] newPerms;
            public final int targetSdk;

            public SplitPermissionInfo(String rootPerm, String[] newPerms, int targetSdk) {
                this.rootPerm = rootPerm;
                this.newPerms = newPerms;
                this.targetSdk = targetSdk;
            }
        }
    }


    public final static class Package {

        public String packageName;

        /**
         * Names of any split APKs, ordered by parsed splitName
         */
        public String[] splitNames;

        // TODO: work towards making these paths invariant

        public String volumeUuid;

        /**
         * Path where this package was found on disk. For monolithic packages
         * this is path to single base APK file; for cluster packages this is
         * path to the cluster directory.
         */
        public String codePath;

        /**
         * Path of base APK
         */
        public String baseCodePath;
        /**
         * Paths of any split APKs, ordered by parsed splitName
         */
        public String[] splitCodePaths;

        /**
         * Revision code of base APK
         */
        public int baseRevisionCode;
        /**
         * Revision codes of any split APKs, ordered by parsed splitName
         */
        public int[] splitRevisionCodes;

        /**
         * Flags of any split APKs; ordered by parsed splitName
         */
        public int[] splitFlags;

        /**
         * Private flags of any split APKs; ordered by parsed splitName.
         * <p>
         * {@hide}
         */
        public int[] splitPrivateFlags;

        public boolean baseHardwareAccelerated;

        // For now we only support one application per package.
        public final ApplicationInfo applicationInfo = new ApplicationInfo();

        public final ArrayList<Permission> permissions = new ArrayList<Permission>(0);
        public final ArrayList<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>(0);
        public final ArrayList<Activity> activities = new ArrayList<Activity>(0);
        public final ArrayList<Activity> receivers = new ArrayList<Activity>(0);
        public final ArrayList<Provider> providers = new ArrayList<Provider>(0);
        public final ArrayList<Service> services = new ArrayList<Service>(0);
        public final ArrayList<Instrumentation> instrumentation = new ArrayList<Instrumentation>(0);

        public final ArrayList<String> requestedPermissions = new ArrayList<String>();

        public ArrayList<String> protectedBroadcasts;

        public Package parentPackage;
        public ArrayList<Package> childPackages;

        public ArrayList<String> libraryNames = null;
        public ArrayList<String> usesLibraries = null;
        public ArrayList<String> usesOptionalLibraries = null;
        public String[] usesLibraryFiles = null;

        public ArrayList<ActivityIntentInfo> preferredActivityFilters = null;

        public ArrayList<String> mOriginalPackages = null;
        public String mRealPackage = null;
        public ArrayList<String> mAdoptPermissions = null;

        // We store the application meta-data independently to avoid multiple unwanted references
        public Bundle mAppMetaData = null;

        // The version code declared for this package.
        public int mVersionCode;

        // The version name declared for this package.
        public String mVersionName;

        // The shared user id that this package wants to use.
        public String mSharedUserId;

        // The shared user label that this package wants to use.
        public int mSharedUserLabel;

        // Signatures that were read from the package.
        public Signature[] mSignatures;
        public Certificate[][] mCertificates;

        // For use by package manager service for quick lookup of
        // preferred up order.
        public int mPreferredOrder = 0;


        public Object mExtras;


        public ArrayList<ConfigurationInfo> configPreferences = null;


        public ArrayList<FeatureInfo> reqFeatures = null;


        public ArrayList<FeatureGroupInfo> featureGroups = null;

        public int installLocation;

        public boolean coreApp;

        /* An app that's required for all users and cannot be uninstalled for a user */
        public boolean mRequiredForAllUsers;

        /* The restricted account authenticator type that is used by this application */
        public String mRestrictedAccountType;

        /* The required account type without which this application will not function */
        public String mRequiredAccountType;

        public String mOverlayTarget;
        public int mOverlayPriority;
        public boolean mTrustedOverlay;

        /**
         * Data used to feed the KeySetManagerService
         */
        public ArraySet<PublicKey> mSigningKeys;
        public ArraySet<String> mUpgradeKeySets;
        public ArrayMap<String, ArraySet<PublicKey>> mKeySetMapping;


        public String cpuAbiOverride;
        public boolean use32bitAbi;

        public byte[] restrictUpdateHash;

        public Package(String packageName) {
            this.packageName = packageName;
            applicationInfo.packageName = packageName;
            applicationInfo.uid = -1;
        }


        public boolean hasChildPackage(String packageName) {
            final int childCount = (childPackages != null) ? childPackages.size() : 0;
            for (int i = 0; i < childCount; i++) {
                if (childPackages.get(i).packageName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }

        public void setSplitCodePaths(String[] codePaths) {
            this.splitCodePaths = codePaths;
        }

        public void setCodePath(String codePath) {
            this.codePath = codePath;
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).codePath = codePath;
                }
            }
        }

        public void setBaseCodePath(String baseCodePath) {
            this.baseCodePath = baseCodePath;
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).baseCodePath = baseCodePath;
                }
            }
        }

        public void setSignatures(Signature[] signatures) {
            this.mSignatures = signatures;
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).mSignatures = signatures;
                }
            }
        }

        public void setVolumeUuid(String volumeUuid) {
            this.volumeUuid = volumeUuid;
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).volumeUuid = volumeUuid;
                }
            }
        }

        public void setApplicationInfoFlags(int mask, int flags) {
            applicationInfo.flags = (applicationInfo.flags & ~mask) | (mask & flags);
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).applicationInfo.flags =
                            (applicationInfo.flags & ~mask) | (mask & flags);
                }
            }
        }

        public void setUse32bitAbi(boolean use32bitAbi) {
            this.use32bitAbi = use32bitAbi;
            if (childPackages != null) {
                final int packageCount = childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    childPackages.get(i).use32bitAbi = use32bitAbi;
                }
            }
        }


        public void setPackageName(String newName) {
            packageName = newName;
            applicationInfo.packageName = newName;
            for (int i = permissions.size() - 1; i >= 0; i--) {
                permissions.get(i).setPackageName(newName);
            }
            for (int i = permissionGroups.size() - 1; i >= 0; i--) {
                permissionGroups.get(i).setPackageName(newName);
            }
            for (int i = activities.size() - 1; i >= 0; i--) {
                activities.get(i).setPackageName(newName);
            }
            for (int i = receivers.size() - 1; i >= 0; i--) {
                receivers.get(i).setPackageName(newName);
            }
            for (int i = providers.size() - 1; i >= 0; i--) {
                providers.get(i).setPackageName(newName);
            }
            for (int i = services.size() - 1; i >= 0; i--) {
                services.get(i).setPackageName(newName);
            }
            for (int i = instrumentation.size() - 1; i >= 0; i--) {
                instrumentation.get(i).setPackageName(newName);
            }
        }

        public boolean hasComponentClassName(String name) {
            for (int i = activities.size() - 1; i >= 0; i--) {
                if (name.equals(activities.get(i).className)) {
                    return true;
                }
            }
            for (int i = receivers.size() - 1; i >= 0; i--) {
                if (name.equals(receivers.get(i).className)) {
                    return true;
                }
            }
            for (int i = providers.size() - 1; i >= 0; i--) {
                if (name.equals(providers.get(i).className)) {
                    return true;
                }
            }
            for (int i = services.size() - 1; i >= 0; i--) {
                if (name.equals(services.get(i).className)) {
                    return true;
                }
            }
            for (int i = instrumentation.size() - 1; i >= 0; i--) {
                if (name.equals(instrumentation.get(i).className)) {
                    return true;
                }
            }
            return false;
        }


        public String toString() {
            return "Package{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " " + packageName + "}";
        }
    }

    public static class Component<II extends IntentInfo> {
        public final Package owner;
        public final ArrayList<II> intents;
        public final String className;
        public Bundle metaData;

        ComponentName componentName;
        String componentShortName;

        public Component(Package _owner) {
            owner = _owner;
            intents = null;
            className = null;
        }

        public Component(final ParsePackageItemArgs args, final PackageItemInfo outInfo) {
            owner = args.owner;
            intents = new ArrayList<II>(0);
            className = outInfo.name;

        }

        public Component(final ParseComponentArgs args, final ComponentInfo outInfo) {
            this(args, (PackageItemInfo) outInfo);
            if (args.outError[0] != null) {
                return;
            }
        }

        public Component(Component<II> clone) {
            owner = clone.owner;
            intents = clone.intents;
            className = clone.className;
            componentName = clone.componentName;
            componentShortName = clone.componentShortName;
        }

        public ComponentName getComponentName() {
            if (componentName != null) {
                return componentName;
            }
            if (className != null) {
                componentName = new ComponentName(owner.applicationInfo.packageName,
                        className);
            }
            return componentName;
        }

        public void appendComponentShortName(StringBuilder sb) {

        }

        public void printComponentShortName(PrintWriter pw) {

        }

        public void setPackageName(String packageName) {
            componentName = null;
            componentShortName = null;
        }
    }

    public final static class Permission extends Component<IntentInfo> {
        public final PermissionInfo info;
        public boolean tree;
        public PermissionGroup group;

        public Permission(Package _owner) {
            super(_owner);
            info = new PermissionInfo();
        }

        public Permission(Package _owner, PermissionInfo _info) {
            super(_owner);
            info = _info;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            return "Permission{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " " + info.name + "}";
        }
    }

    public final static class PermissionGroup extends Component<IntentInfo> {
        public final PermissionGroupInfo info;

        public PermissionGroup(Package _owner) {
            super(_owner);
            info = new PermissionGroupInfo();
        }

        public PermissionGroup(Package _owner, PermissionGroupInfo _info) {
            super(_owner);
            info = _info;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            return "PermissionGroup{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " " + info.name + "}";
        }
    }

    public final static class Activity extends Component<ActivityIntentInfo> {
        public final ActivityInfo info;

        public Activity(final ParseComponentArgs args, final ActivityInfo _info) {
            super(args, _info);
            info = _info;
            info.applicationInfo = args.owner.applicationInfo;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Activity{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }

    public final static class Service extends Component<ServiceIntentInfo> {
        public final ServiceInfo info;

        public Service(final ParseComponentArgs args, final ServiceInfo _info) {
            super(args, _info);
            info = _info;
            info.applicationInfo = args.owner.applicationInfo;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Service{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }

    public final static class Provider extends Component<ProviderIntentInfo> {
        public final ProviderInfo info;
        public boolean syncable;

        public Provider(final ParseComponentArgs args, final ProviderInfo _info) {
            super(args, _info);
            info = _info;
            info.applicationInfo = args.owner.applicationInfo;
            syncable = false;
        }

        public Provider(Provider existingProvider) {
            super(existingProvider);
            this.info = existingProvider.info;
            this.syncable = existingProvider.syncable;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Provider{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }

    public final static class Instrumentation extends Component<IntentInfo> {
        public final InstrumentationInfo info;

        public Instrumentation(final ParsePackageItemArgs args, final InstrumentationInfo _info) {
            super(args, _info);
            info = _info;
        }

        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            info.packageName = packageName;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Instrumentation{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class IntentInfo extends IntentFilter {
        public boolean hasDefault;
        public int labelRes;
        public CharSequence nonLocalizedLabel;
        public int icon;
        public int logo;
        public int banner;
        public int preferred;
    }

    public final static class ActivityIntentInfo extends IntentInfo {
        public final Activity activity;

        public ActivityIntentInfo(Activity _activity) {
            activity = _activity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ActivityIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            activity.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }


    public final static class ServiceIntentInfo extends IntentInfo {
        public final Service service;

        public ServiceIntentInfo(Service _service) {
            service = _service;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ServiceIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            service.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }

    public static final class ProviderIntentInfo extends IntentInfo {
        public final Provider provider;

        public ProviderIntentInfo(Provider provider) {
            this.provider = provider;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ProviderIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            provider.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }
    }


    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int error, String detailMessage) {
            super(detailMessage);
            this.error = error;
        }

        public PackageParserException(int error, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error;
        }
    }

}
