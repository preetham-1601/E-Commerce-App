package com.example.eapp.fragment

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentPaymentsBinding
import com.example.eapp.util.PaymentsUtil
import com.example.eapp.util.SessionManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager: SessionManager

    /*private val SHIPPING_COST_CENTS = 9 * PaymentsUtil.CENTS.toLong()

    /**
     * A client for interacting with the Google Pay API.
     *
     * @see [PaymentsClient](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient)
     */
    private lateinit var paymentsClient: PaymentsClient

    private lateinit var garmentList: JSONArray
    private lateinit var selectedGarment: JSONObject

    /**
     * Arbitrarily-picked constant integer you define to track a request for payment data activity.
     *
     * @value #LOAD_PAYMENT_DATA_REQUEST_CODE
     */
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    *
     * Initialize the Google Pay API on creation of the activity
     *
     * @see Activity.onCreate
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)

        val sun = arguments?.getString("caption")

        val bun = arguments?.getString("image_url")

        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Payments"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun, "image_url" to bun)
            findNavController().navigate(R.id.action_paymentsFragment_to_cartFragment, bundle)
        }

        /*selectedGarment = fetchRandomGarment()
        displayGarment(selectedGarment)

        // Initialize a Google Pay API client for an environment suitable for testing.
        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        paymentsClient = PaymentsUtil.createPaymentsClient(this)
        possiblyShowGooglePayButton()

        googlePayButton.setOnClickListener { requestPayment() }*/

        return view
    }

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
     *
     * @see [](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient.html.isReadyToPay
    ) */
/* private fun possiblyShowGooglePayButton() {

     val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
     val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

     // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
     // OnCompleteListener to be triggered when the result of the call is known.
     val task = paymentsClient.isReadyToPay(request)
     task.addOnCompleteListener { completedTask ->
         try {
             completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
         } catch (exception: ApiException) {
             // Process error
             Log.w("isReadyToPay failed", exception)
         }
     }
 }

 /**
  * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
  * notify the user that Google Pay is not available. Please adjust to fit in with your current
  * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
  *
  * @param available isReadyToPay API response.
  */
 private fun setGooglePayAvailable(available: Boolean) {
     if (available) {
         googlePayButton.visibility = View.VISIBLE
     } else {
         Toast.makeText(
             this,
             "Unfortunately, Google Pay is not available on this device",
             Toast.LENGTH_LONG).show();
     }
 }

 private fun requestPayment() {

     // Disables the button to prevent multiple clicks.
     googlePayButton.isClickable = false

     // The price provided to the API should include taxes and shipping.
     // This price is not displayed to the user.
     val garmentPrice = selectedGarment.getDouble("price")
     val priceCents = Math.round(garmentPrice * PaymentsUtil.CENTS.toLong()) + SHIPPING_COST_CENTS

     val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
     if (paymentDataRequestJson == null) {
         Log.e("RequestPayment", "Can't fetch payment data request")
         return
     }
     val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

     // Since loadPaymentData may show the UI asking the user to select a payment method, we use
     // AutoResolveHelper to wait for the user interacting with it. Once completed,
     // onActivityResult will be called with the result.
     if (request != null) {
         AutoResolveHelper.resolveTask(
             paymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE)
     }
 }

 /**
  * Handle a resolved activity from the Google Pay payment sheet.
  *
  * @param requestCode Request code originally supplied to AutoResolveHelper in requestPayment().
  * @param resultCode Result code returned by the Google Pay API.
  * @param data Intent from the Google Pay API containing payment or error data.
  * @see [Getting a result
  * from an Activity](https://developer.android.com/training/basics/intents/result)
  */
 public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     when (requestCode) {
         // Value passed in AutoResolveHelper
         LOAD_PAYMENT_DATA_REQUEST_CODE -> {
             when (resultCode) {
                 RESULT_OK ->
                     data?.let { intent ->
                         PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                     }

                 RESULT_CANCELED -> {
                     // The user cancelled the payment attempt
                 }

                 AutoResolveHelper.RESULT_ERROR -> {
                     AutoResolveHelper.getStatusFromIntent(data)?.let {
                         handleError(it.statusCode)
                     }
                 }
             }

             // Re-enables the Google Pay payment button.
             googlePayButton.isClickable = true
         }
     }
 }

 /**
  * PaymentData response object contains the payment information, as well as any additional
  * requested information, such as billing and shipping address.
  *
  * @param paymentData A response object returned by Google after a payer approves payment.
  * @see [Payment
  * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
  */
 private fun handlePaymentSuccess(paymentData: PaymentData) {
     val paymentInformation = paymentData.toJson() ?: return

     try {
         // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
         val paymentMethodData = JSONObject(paymentInformation).getJSONObject("paymentMethodData")
         val billingName = paymentMethodData.getJSONObject("info")
             .getJSONObject("billingAddress").getString("name")
         Log.d("BillingName", billingName)

         Toast.makeText(this, getString(R.string.payments_show_name, billingName), Toast.LENGTH_LONG).show()

         // Logging token string.
         Log.d("GooglePaymentToken", paymentMethodData
             .getJSONObject("tokenizationData")
             .getString("token"))

     } catch (e: JSONException) {
         Log.e("handlePaymentSuccess", "Error: " + e.toString())
     }

 }
 /*
     *
      * At this stage, the user has already seen a popup informing them an error occurred. Normally,
      * only logging is required.
      *
      * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
      * WalletConstants.ERROR_CODE_* constants.
      * @see [
      * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
      */
 /*private fun handleError(statusCode: Int) {
     Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
 }

 private fun fetchRandomGarment() : JSONObject {
     if (!::garmentList.isInitialized) {
         garmentList = Json.readFromResources(this, R.raw.tshirts)
     }

     val randomIndex:Int = Math.round(Math.random() * (garmentList.length() - 1)).toInt()
     return garmentList.getJSONObject(randomIndex)
 }

 private fun displayGarment(garment:JSONObject) {
     detailTitle.setText(garment.getString("title"))
     detailPrice.setText("\$${garment.getString("price")}")

     val escapedHtmlText:String = Html.fromHtml(garment.getString("description")).toString()
     detailDescription.setText(Html.fromHtml(escapedHtmlText))

     val imageUri = "@drawable/${garment.getString("image")}"
     val imageResource = resources.getIdentifier(imageUri, null, packageName)
     detailImage.setImageResource(imageResource)
 }
}*/



 */

}