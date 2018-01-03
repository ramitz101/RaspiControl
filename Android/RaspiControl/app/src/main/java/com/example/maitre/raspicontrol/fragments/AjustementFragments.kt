package com.example.maitre.raspicontrol.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast

import com.example.maitre.raspicontrol.R
import kotlinx.android.synthetic.main.fragment_ajustements.*
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AjustementFragments.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AjustementFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class AjustementFragments(var mmSock:BluetoothSocket?,var mmDev:BluetoothDevice?) : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var pick: NumberPicker? = null

    private var outputStream : OutputStream? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

        //outputStream = mmSock!!.outputStream







    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_ajustements, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        numberPickerKD.minValue = 0;
        numberPickerKD.maxValue = 300;
        numberPickerKI.minValue = 0;
        numberPickerKI.maxValue = 300;
        numberPickerKP.minValue = 0;
        numberPickerKP.maxValue = 300;

        btnEnvoye.setOnClickListener{
            //Toast.makeText(this.context,outputStream.toString(),Toast.LENGTH_LONG).show()
            //Toast.makeText(this.context,numberPickerKD.value.toString(),Toast.LENGTH_LONG).show()
            var MON_UUID : UUID = UUID.fromString("7be1fcb3-5776-42fb-91fd-2ee7b5bbb86d")
            mmSock = mmDev!!.createRfcommSocketToServiceRecord(MON_UUID)
            mmSock!!.connect()

            outputStream = mmSock!!.outputStream

            var donne : String = "Ajustement "+numberPickerKP.value.toString() +"-"+ numberPickerKI.value.toString()+"-" + numberPickerKD.value.toString()

            var b : ByteArray = donne.toByteArray(Charset.forName("UTF-8"))
            outputStream!!.write(b)

            //var sw =  OutputStreamWriter(mmSock!!.outputStream)

            /*var go = Thread( Runnable {
                sw.write("example")
            }).start()*/




            //mmSock!!.close()


        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AjustementFragments.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: BluetoothSocket?, param2: BluetoothDevice?): AjustementFragments {
            val fragment = AjustementFragments(param1,param2)
            val args = Bundle()

            args.putString(ARG_PARAM1, "")
            args.putString(ARG_PARAM2, "")
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
