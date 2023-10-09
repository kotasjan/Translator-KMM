//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by Jan Kotas on 09.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLanguageIcon: View {
    var language: UiLanguage
    var body: some View {
        Image(uiImage: UIImage(named: language.imageName.lowercased())!)
            .resizable()
            .frame(width: 30, height: 30)
    }
}

#Preview {
    SmallLanguageIcon(language: UiLanguage(language: .czech, imageName: "czech"))
}
