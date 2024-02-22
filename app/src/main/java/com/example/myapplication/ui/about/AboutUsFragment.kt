package com.example.myapplication.ui.about

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.databinding.AboutUsFragmentBinding

class AboutUsFragment : Fragment() {

    private var _binding: AboutUsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutUsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Remplissez ici les détails de votre page "About Us"
        val aboutText = "Bienvenue sur notre application !"
        binding.aboutTextView.text = aboutText

        val aboutText2 =
            "Nous sommes une équipe passionnée de développement d'applications mobiles. " +
                    "Nous espérons que vous apprécierez notre application autant que nous avons aimé la créer."
        binding.textView2.text = aboutText2

        val teamMembers = listOf(
            "Lucas Escaffre - Développeur Android",
            "Anthony Cavagne - Développeur Android"
        )
        binding.teamMembersTextView.text = teamMembers.joinToString("\n")

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
