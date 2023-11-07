//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    var language: UiLanguage
    
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 5)
            Text(language.language.langName)
                .foregroundColor(Shared.colors.lightBlue)
        }
    }
}

#Preview {
    LanguageDisplay(language: UiLanguage(language: Language.czech, imageName: "czech"))
}
