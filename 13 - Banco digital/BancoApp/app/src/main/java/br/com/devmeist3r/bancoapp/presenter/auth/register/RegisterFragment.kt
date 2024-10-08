package br.com.devmeist3r.bancoapp.presenter.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.devmeist3r.bancoapp.R
import br.com.devmeist3r.bancoapp.data.model.User
import br.com.devmeist3r.bancoapp.data.model.Wallet
import br.com.devmeist3r.bancoapp.databinding.FragmentRegisterBinding
import br.com.devmeist3r.bancoapp.presenter.profile.ProfileViewModel
import br.com.devmeist3r.bancoapp.presenter.wallet.WalletViewModel
import br.com.devmeist3r.bancoapp.util.FirebaseHelper
import br.com.devmeist3r.bancoapp.util.StateView
import br.com.devmeist3r.bancoapp.util.initToolbar
import br.com.devmeist3r.bancoapp.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val walletViewModel: WalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSend.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.unMaskedText
        val password = binding.editPassword.text.toString().trim()

        if (name.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (phone?.isNotEmpty() == true) {
                    if (phone.length == 11) {
                        if (password.isNotEmpty()) {
                            binding.circularProgressIndicator.isVisible = true
                            registerUser(name, email, phone, password)
                        } else {
                            showBottomSheet(message = getString(R.string.text_password_empty))
                        }
                    } else {
                        showBottomSheet(message = getString(R.string.text_phone_invalid))
                    }
                } else {
                    showBottomSheet(message = getString(R.string.text_phone_empty))
                }
            } else {
                showBottomSheet(message = getString(R.string.text_email_empty))
            }
        } else {
            showBottomSheet(message = getString(R.string.text_name_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        registerViewModel.register(name, email, phone, password)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                        binding.circularProgressIndicator.isVisible = true
                    }

                    is StateView.Success -> {
                        stateView.data?.let { saveProfile(it) }
                    }

                    is StateView.Error -> {
                        binding.circularProgressIndicator.isVisible = false
                        showBottomSheet(
                            message = getString(
                                FirebaseHelper.validError(
                                    stateView.message ?: ""
                                )
                            )
                        )
                    }
                }
            }
    }

    private fun saveProfile(
        user: User
    ) {
        profileViewModel.saveProfile(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}

                is StateView.Success -> {
                    initWallet()
                }

                is StateView.Error -> {
                    binding.circularProgressIndicator.isVisible = false
                    showBottomSheet(
                        message = getString(
                            FirebaseHelper.validError(
                                stateView.message ?: ""
                            )
                        )
                    )
                }
            }
        }
    }

    private fun initWallet() {
        walletViewModel.initWallet(
            Wallet(
                userId = FirebaseHelper.getUserId()
            )
        ).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}

                is StateView.Success -> {
                    binding.circularProgressIndicator.isVisible = false
                    findNavController().navigate(R.id.action_global_homeFragment)
                }

                is StateView.Error -> {
                    binding.circularProgressIndicator.isVisible = false
                    showBottomSheet(
                        message = getString(
                            FirebaseHelper.validError(
                                stateView.message ?: ""
                            )
                        )
                    )
                }
            }
        }
    }
}