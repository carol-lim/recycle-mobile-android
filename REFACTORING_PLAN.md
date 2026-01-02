# Project Charter: MYRecycleApp Modernization & Refactoring (v2.1)

## 1. Executive Vision

The goal of this project is to refactor `MYRecycleApp` from its current state into a best-in-class mobile application for the Malaysian market. We will focus on creating a frictionless, high-engagement user experience, implementing intelligent AI-driven features, and building a robust, scalable backend architecture. This will result in a high-performance, stable, and maintainable platform poised for significant user growth and operational efficiency.

## 2. Core Project Goals

- **Increase User Engagement:** Make the app a rewarding and interactive tool that users return to, not just for pickups but for education and progress tracking.
- **Boost User Retention:** Create a seamless and valuable experience that encourages long-term use.
- **Streamline Operations:** Optimize the pickup and collection process for both Residents and Collectors.
- **Establish a Scalable Platform:** Rebuild the app's foundation to handle significant growth in users and data without degradation in performance.
- **Enhance Data Insights:** Provide powerful data analysis capabilities for the administrative team to make informed business decisions.

## 3. Problem Analysis & Proposed Solutions

| Problem Identified | Impact | Proposed Solution |
| :--- | :--- | :--- |
| **High-Friction Onboarding** | Users abandon registration due to the multi-field form, a major drop-off point. | Implement **one-tap social login** (Google Sign-In). Defer address and other data collection until the moment it's needed (the first reservation). |
| **Unengaging AI Feature** | The current AI is a mandatory hurdle, not a feature, hurting the user experience. | Reposition the AI as an engaging, optional tool: **"Can I Recycle This?"**. Gamify the experience with value estimates and fun facts to drive interaction. |
| **Confusing Category Selection** | Forcing users to pick one specific category per reservation is inefficient and unrealistic. | Allow users to select **multiple categories** or a general **"Mixed Recycling"** option, simplifying the reservation process dramatically. |
| **Slow, Unscalable Database** | The flat Firebase structure requires downloading all data, leading to slow load times and high mobile data costs for users. | **Refactor the Firebase schema to Firestore**, making it user-centric and denormalized. Use Cloud Functions to pre-calculate user statistics for instant chart loading. |
| **Hard-Coded, Static Charts** | Charts do not reflect real user data, providing no value or sense of progression. | Connect UI charts to the new, refactored Firestore database to display **real-time, personalized user statistics** (earnings, weight recycled, environmental impact). |
| **Outdated User Experience** | The multi-activity structure and map-based homepage are confusing and inefficient for the app's purpose. | Implement a **Single-Activity Architecture** with a modern dashboard homepage. Convert the map into a **smart address selection utility** with Google Places autocomplete. |
| **No Admin Oversight** | There is no way to manage users, update prices, or view system-wide analytics, hindering business operations. | Create a secure, web-based **Admin Dashboard** for platform management, user administration, and data analysis. |
| **CRITICAL: Insecure & Irrelevant Payment Gateway** | The app uses `paypal-android-sdk:2.14.2`, which is deprecated, insecure, and poorly suited for the Malaysian market. | Replace PayPal entirely with **Stripe** as the primary payment gateway. Implement a phased rollout starting with **direct-to-bank payouts (Phase 1)** and adding **TNG eWallet payouts (Phase 2)**. |

## 4. User Roles & Personas

We will build the platform to support three distinct user roles:

1.  **Resident:** The primary user of the app.
    -   **Actions:** Schedules pickups, uses the AI scanner, tracks earnings and environmental impact, manages their addresses and payout methods.
2.  **Collector:** The service provider.
    -   **Actions:** Views and accepts pickup requests in their area, navigates to addresses, weighs materials, confirms collection details, and tracks their earnings.
3.  **Admin:** The platform owner/operator.
    -   **Actions (via Web Dashboard):** Manages the recycling catalog and pricing, oversees all transactions, manages user accounts, and views system-wide analytics.

## 5. System & Application Architecture

#### 5.1. Application Architecture: Modern Android Development

- **Model-View-ViewModel (MVVM):** To separate UI logic from business logic.
- **Single-Activity Architecture:** A single `MainActivity` will host various `Fragment` destinations, managed by the **Jetpack Navigation Component**.
- **Jetpack Compose:** We will migrate the entire UI from legacy XML layouts to Jetpack Compose for a faster, more responsive, and maintainable UI.
- **Repository Pattern:** To abstract data sources (Firestore, network APIs) from the ViewModels.
- **Dependency Injection with Hilt:** To manage dependencies, making the code modular and highly testable.
- **Kotlin First:** All new code will be written in Kotlin. Existing Java files will be migrated to Kotlin as they are refactored.

#### 5.2. System Architecture: A Cloud-Native Ecosystem

1.  **Android Application:** The resident and collector-facing app.
2.  **Firebase Backend:**
    -   **Firestore:** The primary NoSQL database, using the new, scalable schema.
    -   **Firebase Authentication:** For secure Google Sign-In.
    -   **Cloud Functions:** For trusted backend logic (e.g., calculating stats, triggering payouts via the Stripe API).
    -   **Cloud Storage:** For hosting images of collected items.
3.  **Third-Party Services:**
    -   **Stripe:** Securely processing all financial payouts to Residents.
    -   **Google Maps Platform:** For address autocomplete (Places API) and map visualization.
4.  **Web Admin Dashboard:** A React or Vue.js web application on Firebase Hosting for the Admin role.

## 6. Refactored Database Schema (Firestore)

'''json
{
  "users": {
    "{userID}": {
      "profile": {
        "name": "Lim Yen Hua",
        "email": "carol.0412@hotmail.com",
        "phone": "+60127889449",
        "userType": "resident", // "resident" or "collector"
        "createdAt": "timestamp"
      },
      "addresses": {
        "{addressID}": {
          "label": "Home",
          "fullAddress": "14, Jalan Emas 2, Taman Bukit Melaka...",
          "geopoint": "lat,lng"
        }
      },
      "stats": { // Denormalized for fast profile loading
        "totalWeightKg": 105.46,
        "totalEarnings": 113.1,
        "monthlyEarnings": { "2023-11": 50.5, "2023-10": 62.6 },
        "categoryTotalsKg": { "pet_plastic": 36, "cardboard": 9.56 }
      }
    }
  },
  "reservations": {
    "{reservationID}": {
      "residentUid": "{userID}",
      "collectorUid": "{collectorID}", // Null until accepted
      "status": "completed", // e.g., 'pending', 'accepted', 'in_progress', 'completed', 'cancelled'
      "createdAt": "timestamp",
      "completedAt": "timestamp",
      "address": {
          "label": "Home",
          "fullAddress": "14, Jalan Emas 2..."
      },
      "items": [
        { "category": "pet_plastic", "weightKg": 5.0, "price": 3.0 },
        { "category": "cardboard", "weightKg": 2.1, "price": 0.84 }
      ],
      "totalPrice": 3.84,
      "totalWeightKg": 7.1
    }
  },
  "catalog": {
    "pet_plastic": {
      "name": "Plastic Bottles",
      "desc": "PET, or PolyEthylene Terephthalate...",
      "pricePerKg": 0.6,
      "imageUrl": "...",
      "isActive": true
    }
  }
}
'''

## 7. Monetization & Payment Gateway Strategy

This strategy is designed to provide secure, low-cost, and high-engagement payout options tailored for the Malaysian market.

- **Selected Gateway: Stripe**
    - We will use Stripe for its excellent developer APIs, robust security, transparent pricing, and strong support for the Malaysian market.
- **Phase 1: Foundational Payouts (The Foundation)**
    - **Method:** Direct-to-bank payouts via Stripe's integration with Malaysian banking networks.
    - **Goal:** Establish a universal, reliable, and secure payout system covering 100% of the user base.
- **Phase 2: Engagement-Driven Payouts (The "Wow" Factor)**
    - **Method:** Instant payouts to the Resident's **Touch 'n Go (TNG) eWallet**.
    - **Goal:** Leverage Malaysia's most popular e-wallet to create a powerful engagement loop.

#### Estimated Gateway Costs (Operational Fees)

These are ongoing fees charged by Stripe on a per-transaction basis.

- **Implementation/Setup Fee:** **RM 0**
- **Annual/Monthly Fee:** **RM 0**
- **Payout to Bank Account (Per Transaction):** **~RM 0.50 - RM 1.50 (flat fee)**
- **Payout to TNG eWallet (Per Transaction):** **To Be Confirmed** (Likely a small flat fee or a low percentage, e.g., 1-2%).

## 8. Technology & Dependency Overhaul Plan

Based on the `build.gradle` file, we will perform a comprehensive dependency modernization:

- **Adopt Bill of Materials (BOM):** Use the Firebase and Kotlin Coroutines BOMs to manage library versions cohesively and prevent conflicts.
- **Update Core Libraries:** Upgrade all major libraries to their latest stable versions, including:
    - `androidx.appcompat:appcompat:1.4.1` -> `1.6.1+`
    - `com.google.android.material:material:1.5.0` -> `1.10.0+`
    - `androidx.constraintlayout:constraintlayout:2.1.3` -> `2.1.4+`
    - `androidx.navigation:navigation-*:2.3.5` -> `2.7.5+`
- **Modernize Firebase:** Update all Firebase libraries (`firebase-database:20.0.4`, `firebase-auth:21.0.3`, etc.) to the latest versions using the BOM for better performance and new features. We will migrate from the Realtime Database to **Firestore**.
- **Modernize AI/ML:** Replace the legacy `org.tensorflow:tensorflow-lite-support:0.1.0` with the modern **`org.tensorflow:tensorflow-lite-task-vision`** library. This will provide a high-level API for image classification, reducing boilerplate code and improving performance.
- **CRITICAL - Replace Payment Gateway:**
    - The `com.paypal.sdk:paypal-android-sdk:2.14.2` dependency will be **completely removed**.
    - It will be replaced with the official **`com.stripe:stripe-android`** SDK to handle the secure, in-app collection of user payout details.
- **General Cleanup:** The `androidx.legacy:legacy-support-v4:1.0.0` dependency will be removed as we migrate to modern AndroidX components and a Single-Activity architecture.
