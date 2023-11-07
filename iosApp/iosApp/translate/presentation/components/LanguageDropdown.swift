//
//  LanguageDropdown.swift
//  iosApp
//
//  Created by Jan Kotas on 09.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropdown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu {
            VStack{
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                            selectLanguage(language)
                        }
                    )
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                Text(language.language.langName)
                    .foregroundColor(Shared.colors.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(Shared.colors.lightBlue)
            }
        }

    }
}

#Preview {
    LanguageDropdown(
        language: UiLanguage(language: .czech, imageName: "czech"),
        isOpen: true,
        selectLanguage: { langugage in }
    )
}
