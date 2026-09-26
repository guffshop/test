package com.example.data.datasource

import com.example.data.model.CategoryType
import com.example.data.model.CourseClass
import com.example.data.model.DownloadableAsset
import com.example.data.model.VideoLesson

object PredefinedClasses {

    val allClasses: List<CourseClass> by lazy {
        listOf(
            // ==========================================
            // PHOTOSHOP CLASS
            // ==========================================
            CourseClass(
                id = "ps-class-1",
                category = CategoryType.PHOTOSHOP,
                title = "Photoshop Masterclass: Zero to Pro",
                subtitle = "Master workspace, non-destructive layers & precision selections",
                description = "Build a bulletproof foundation in Adobe Photoshop. Learn professional workflow habits, advanced pen tool cuts, layer clipping masks, and smart object integration.",
                level = "Beginner to Intermediate",
                durationText = "2h 45m • 4 Videos",
                instructorName = "Alex Rivera",
                instructorTitle = "Senior Adobe Certified Expert",
                badge = "POPULAR",
                lessons = listOf(
                    VideoLesson(
                        id = "ps-1-01",
                        classId = "ps-class-1",
                        title = "1. Pro Workspace Setup & Non-Destructive Workflow",
                        lessonNumber = 1,
                        durationText = "18:42",
                        youtubeVideoId = "dQw4w9WgXcQ", // unlisted YouTube ID
                        description = "Setup custom shortcuts, color settings (sRGB vs ProPhoto), scratch disks, and organize layer hierarchies like high-end design agencies.",
                        keyPoints = listOf(
                            "Keyboard shortcuts for 3x editing speed",
                            "Understanding bit depth (8-bit vs 16-bit)",
                            "Mastering Smart Objects to prevent pixel destruction"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Photoshop_Cheatsheet_2026.pdf", "PDF Guide", "4.2 MB", "https://example.com/assets/ps_cheatsheet.pdf"),
                            DownloadableAsset("Starter_Workspace_Layout.kys", "Workspace File", "120 KB", "https://example.com/assets/workspace.kys")
                        )
                    ),
                    VideoLesson(
                        id = "ps-1-02",
                        classId = "ps-class-1",
                        title = "2. Pen Tool Mastery & Complex Selections",
                        lessonNumber = 2,
                        durationText = "24:15",
                        youtubeVideoId = "L_LUpnjgPso",
                        description = "Tackle difficult cutouts including hair refinement, semi-transparent fabrics, and hard geometric shapes using vector paths and Select & Mask workspace.",
                        keyPoints = listOf(
                            "Bezier curve control: smooth anchors and cusp points",
                            "Refine edge brush for realistic hair strands",
                            "Channel masking techniques for high-contrast subjects"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Hair_Masking_Practice_Pack.zip", "RAW Photos", "48 MB", "https://example.com/assets/hair_practice.zip"),
                            DownloadableAsset("Pen_Tool_Curves_Template.psd", "PSD Exercise", "14 MB", "https://example.com/assets/pen_exercise.psd")
                        )
                    ),
                    VideoLesson(
                        id = "ps-1-03",
                        classId = "ps-class-1",
                        title = "3. Layer Masks, Blending Modes & Luminosity",
                        lessonNumber = 3,
                        durationText = "22:10",
                        youtubeVideoId = "kJQP7kiw5Fk",
                        description = "Harness the true power of Screen, Multiply, Overlay, and Soft Light. Create custom luminosity masks to isolate highlights and deep shadows.",
                        keyPoints = listOf(
                            "Blend If sliders for seamless compositing",
                            "Clipping mask chains for grouped effects",
                            "Protecting shadows from clipping with curve adjustments"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Blending_Modes_Visual_Guide.pdf", "Cheat Sheet", "2.8 MB", "https://example.com/assets/blend_guide.pdf")
                        )
                    ),
                    VideoLesson(
                        id = "ps-1-04",
                        classId = "ps-class-1",
                        title = "4. Frequency Separation & High-End Skin Retouching",
                        lessonNumber = 4,
                        durationText = "35:30",
                        youtubeVideoId = "3JZ_D3ELwOQ",
                        description = "Separate texture from color and tone. Eliminate blemishes, smooth tonal transitions, and retain natural pores for magazine-grade portraits.",
                        keyPoints = listOf(
                            "Setting up 16-bit High and Low frequency layers",
                            "Healing brush vs clone stamp on texture layer",
                            "Dodge and Burn curves for dimensional contouring"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Frequency_Separation_1Click_Action.atn", "Photoshop Action", "85 KB", "https://example.com/assets/action_fs.atn"),
                            DownloadableAsset("Studio_Portrait_HighRes.psd", "Full Project PSD", "110 MB", "https://example.com/assets/portrait_project.psd")
                        )
                    )
                )
            ),
            CourseClass(
                id = "ps-class-2",
                category = CategoryType.PHOTOSHOP,
                title = "Photoshop Compositing & Sci-Fi Concept Art",
                subtitle = "Blend perspective, atmospheric fog & realistic cinematic lighting",
                description = "Learn how Hollywood digital artists combine multiple stock images into a breathtaking cinematic concept artwork with consistent perspective and light sources.",
                level = "Advanced",
                durationText = "3h 10m • 3 Videos",
                instructorName = "Maya Lin",
                instructorTitle = "VFX Matte Painter & Concept Artist",
                badge = "ADVANCED",
                lessons = listOf(
                    VideoLesson(
                        id = "ps-2-01",
                        classId = "ps-class-2",
                        title = "1. Matching Horizon, Scale & Horizon Lines",
                        lessonNumber = 1,
                        durationText = "28:50",
                        youtubeVideoId = "fJ9rUzIMcZQ",
                        description = "Learn the 1-point and 2-point perspective grid setup inside Photoshop so elements feel grounded in the same 3D physical world.",
                        keyPoints = listOf(
                            "Vanishing point tool and guide planes",
                            "Match eye-level across disparate stock photos",
                            "Lens distortion correction"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("SciFi_Matte_Assets_Pack.zip", "Stock Pack", "85 MB", "https://example.com/assets/matte_assets.zip")
                        )
                    ),
                    VideoLesson(
                        id = "ps-2-02",
                        classId = "ps-class-2",
                        title = "2. Painting Volumetric Light & Atmospheric Depth",
                        lessonNumber = 2,
                        durationText = "32:15",
                        youtubeVideoId = "RgKAFK5djSk",
                        description = "Create realistic god rays, floating dust particles, and atmospheric haze using custom brushes and gradient map grading.",
                        keyPoints = listOf(
                            "Atmospheric perspective: contrast drop over distance",
                            "Painting light wrap around subjects",
                            "Rim lighting techniques for subjects against bright backdrops"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Atmosphere_And_Light_Brushes.abr", "Custom Brushes", "18 MB", "https://example.com/assets/light_brushes.abr")
                        )
                    ),
                    VideoLesson(
                        id = "ps-2-03",
                        classId = "ps-class-2",
                        title = "3. Final Color Harmonization & Film Grain",
                        lessonNumber = 3,
                        durationText = "24:40",
                        youtubeVideoId = "kffacxfA7G4",
                        description = "Unify distinct photos with Color Lookup tables, Selective Color adjustment layers, and realistic 35mm film grain overlay.",
                        keyPoints = listOf(
                            "Gradient map grading for movie poster aesthetics",
                            "High-pass sharpening with threshold masking",
                            "Exporting TIFF and full quality web deliverables"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Cinematic_Color_LUTs_Pack.cube", "LUT Pack", "6.4 MB", "https://example.com/assets/cinematic_luts.cube")
                        )
                    )
                )
            ),

            // ==========================================
            // MOBILE VIDEOGRAPHY
            // ==========================================
            CourseClass(
                id = "mv-class-1",
                category = CategoryType.MOBILE_VIDEOGRAPHY,
                title = "Cinematic Phone Filmmaking Essentials",
                subtitle = "Turn your iPhone or Android into a professional cinema rig",
                description = "Unlock the true potential of your smartphone camera. Learn manual exposure, 24fps cinema rules, custom bitrates, and how to eliminate the cheap smartphone look.",
                level = "All Levels",
                durationText = "2h 15m • 4 Videos",
                instructorName = "Marcus Cruz",
                instructorTitle = "Commercial Cinematographer & Director",
                badge = "ESSENTIAL",
                lessons = listOf(
                    VideoLesson(
                        id = "mv-1-01",
                        classId = "mv-class-1",
                        title = "1. Unlocking 24fps, Shutter Rule & Native Camera Secrets",
                        lessonNumber = 1,
                        durationText = "16:20",
                        youtubeVideoId = "9bZkp7q19f0",
                        description = "Why 60fps makes your videos look like home video, and how the 180-degree shutter rule creates natural motion blur on your phone.",
                        keyPoints = listOf(
                            "24fps vs 30fps vs 60fps breakdown",
                            "Using ND filters (Variable ND) on phone lenses",
                            "Locking AE/AF and adjusting ISO to prevent digital noise"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Mobile_Cinematography_Guide.pdf", "PDF Field Guide", "5.1 MB", "https://example.com/assets/mobile_cine.pdf")
                        )
                    ),
                    VideoLesson(
                        id = "mv-1-02",
                        classId = "mv-class-1",
                        title = "2. 3-Point Lighting on a Budget with Household Lamps",
                        lessonNumber = 2,
                        durationText = "21:40",
                        youtubeVideoId = "CevxZvSJLk8",
                        description = "Key light, fill light, and backlight. How to shape light on faces and create deep contrast without spending thousands on cinema gear.",
                        keyPoints = listOf(
                            "Diffuse harsh light with shower curtains or bedsheets",
                            "Motivated lighting: making light look natural to the scene",
                            "Color temperature balance: 3200K tungsten vs 5600K daylight"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Lighting_Setup_Diagrams.pdf", "Diagrams", "3.2 MB", "https://example.com/assets/lighting_diagrams.pdf")
                        )
                    ),
                    VideoLesson(
                        id = "mv-1-03",
                        classId = "mv-class-1",
                        title = "3. Smooth Handheld & Gimbal Movements Without Jitter",
                        lessonNumber = 3,
                        durationText = "19:15",
                        youtubeVideoId = "Zi_XLOR8gac",
                        description = "The ninja walk, parallax push-in, reveal shots, and orbital tracking moves that make phone footage look like a $10,000 crane setup.",
                        keyPoints = listOf(
                            "Ninja-walk mechanics to prevent vertical z-axis bob",
                            "Low-angle tracking with ultra-wide lens",
                            "Whip pan transitions shot directly in-camera"
                        )
                    ),
                    VideoLesson(
                        id = "mv-1-04",
                        classId = "mv-class-1",
                        title = "4. Crystal Clear Audio Recording on Mobile",
                        lessonNumber = 4,
                        durationText = "17:35",
                        youtubeVideoId = "OPf0YbXqDm0",
                        description = "Viewers tolerate 720p video but will leave immediately if audio is bad. How to capture clean podcast-grade sound on wireless lavs.",
                        keyPoints = listOf(
                            "Wireless mic placement and hiding under shirts",
                            "Room acoustics: eliminating echo with soft furnishings",
                            "Monitoring levels to prevent clipping and distortion"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Audio_Gear_Recommendation_List.pdf", "Gear List", "1.1 MB", "https://example.com/assets/audio_gear.pdf")
                        )
                    )
                )
            ),
            CourseClass(
                id = "mv-class-2",
                category = CategoryType.MOBILE_VIDEOGRAPHY,
                title = "Product Videography & Commercial Ads on Phone",
                subtitle = "Shoot high-end commercials for luxury perfumes, sneakers & food",
                description = "Master commercial lighting, rotating turntables, macro details, and dynamic speed ramping tailored for client work and social media brand ads.",
                level = "Intermediate",
                durationText = "1h 50m • 3 Videos",
                instructorName = "Elena Rostova",
                instructorTitle = "Commercial Brand Video Producer",
                badge = "COMMERCIAL",
                lessons = listOf(
                    VideoLesson(
                        id = "mv-2-01",
                        classId = "mv-class-2",
                        title = "1. Macro Lens Secrets & Turntable Product Staging",
                        lessonNumber = 1,
                        durationText = "22:15",
                        youtubeVideoId = "hT_nvWreIhg",
                        description = "How to clean products, position polarizing filters to eliminate glare, and create glossy commercial bottle reflections.",
                        keyPoints = listOf(
                            "Linear polarizing filters for glass and liquids",
                            "Continuous light strips for crisp edge highlights",
                            "Synchronizing camera moves with motor turntable"
                        )
                    ),
                    VideoLesson(
                        id = "mv-2-02",
                        classId = "mv-class-2",
                        title = "2. Water Splashes, Smoke & Practical SFX",
                        lessonNumber = 2,
                        durationText = "25:30",
                        youtubeVideoId = "kJQP7kiw5Fk",
                        description = "Capture high-speed liquid splashes and atmospheric smoke rings using phone 240fps slow-motion without quality degradation.",
                        keyPoints = listOf(
                            "Lighting for 240fps slow-mo without light flicker",
                            "Glycerin and water mix for long-lasting droplets",
                            "Timing cues for splash choreography"
                        )
                    ),
                    VideoLesson(
                        id = "mv-2-03",
                        classId = "mv-class-2",
                        title = "3. Client Pitching & Pricing Mobile Video Projects",
                        lessonNumber = 3,
                        durationText = "19:00",
                        youtubeVideoId = "3JZ_D3ELwOQ",
                        description = "How to pitch local businesses and e-commerce brands $1,500 to $3,000 for monthly short-form video content packages.",
                        keyPoints = listOf(
                            "Contract templates and revision limit clauses",
                            "Creating a portfolio reel that closes clients",
                            "Upselling ad variations and vertical cutdowns"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Commercial_Video_Contract_Template.docx", "Docx Template", "450 KB", "https://example.com/assets/contract.docx")
                        )
                    )
                )
            ),

            // ==========================================
            // CAPCUT EDITING
            // ==========================================
            CourseClass(
                id = "capcut-class-1",
                category = CategoryType.CAPCUT,
                title = "Viral Short-Form Editing in CapCut (Pro & Mobile)",
                subtitle = "Master retention graphs, speed ramps, smooth zooms & sound design",
                description = "Learn the exact editing techniques used by the top 1% of content creators to hold viewer attention for 60+ seconds and trigger viral social algorithms.",
                level = "All Levels",
                durationText = "2h 30m • 4 Videos",
                instructorName = "Jordan K.",
                instructorTitle = "100M+ Views Short-Form Video Editor",
                badge = "HOT & TRENDING",
                lessons = listOf(
                    VideoLesson(
                        id = "cc-1-01",
                        classId = "capcut-class-1",
                        title = "1. Pacing & The 3-Second Visual Retention Rule",
                        lessonNumber = 1,
                        durationText = "21:10",
                        youtubeVideoId = "dQw4w9WgXcQ",
                        description = "Cut dead air instantly. Master J-cuts and L-cuts to make speech flow seamlessly. Keep visual stimulus changing every 2.5 to 3 seconds.",
                        keyPoints = listOf(
                            "Removing breath gaps with auto-split precision",
                            "B-roll insertion without losing audio cadence",
                            "Match-cutting action points for subconscious continuity"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Retention_Pacing_Cheatsheet.pdf", "PDF Guide", "2.1 MB", "https://example.com/assets/retention_pacing.pdf")
                        )
                    ),
                    VideoLesson(
                        id = "cc-1-02",
                        classId = "capcut-class-1",
                        title = "2. Speed Ramping with Optical Flow Smoothness",
                        lessonNumber = 2,
                        durationText = "26:45",
                        youtubeVideoId = "L_LUpnjgPso",
                        description = "Transform ordinary movement into high-energy viral ramps using custom speed curves and AI Optical Flow motion interpolation.",
                        keyPoints = listOf(
                            "Hero ramp curve: 5x zoom into 0.2x slow-mo on the impact beat",
                            "Enabling CapCut Optical Flow to prevent stutter",
                            "Pairing whoosh and bass drop sound effects with speed ramp"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("50_Viral_Whoosh_And_Hit_SFX.zip", "Audio Pack", "34 MB", "https://example.com/assets/viral_sfx.zip")
                        )
                    ),
                    VideoLesson(
                        id = "cc-1-03",
                        classId = "capcut-class-1",
                        title = "3. Kinetic Captions, Word Highlights & Glow Effects",
                        lessonNumber = 3,
                        durationText = "23:30",
                        youtubeVideoId = "kJQP7kiw5Fk",
                        description = "Generate auto-captions and stylize them with bouncy pop animations, colored keyword tags, drop shadows, and glowing emoji stickers.",
                        keyPoints = listOf(
                            "CapCut auto-caption styling & custom font import",
                            "Active word highlight animations",
                            "Sound effect synchronization for every major keyword"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Trending_Creator_Fonts_Pack.zip", "Fonts (TTF/OTF)", "12 MB", "https://example.com/assets/creator_fonts.zip")
                        )
                    ),
                    VideoLesson(
                        id = "cc-1-04",
                        classId = "capcut-class-1",
                        title = "4. Layering SFX: Whooshes, Risers, Pops & Ambience",
                        lessonNumber = 4,
                        durationText = "19:50",
                        youtubeVideoId = "3JZ_D3ELwOQ",
                        description = "Sound design accounts for 60% of viewer engagement. Learn how to mix 4 audio layers: Dialogue, SFX, Rhythmic Beats, and Background Texture.",
                        keyPoints = listOf(
                            "Audio ducking: lower music automatically during speech",
                            "Stereo panning for immersive headphone listening",
                            "Equalization tricks to make voice punch through heavy beats"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("CapCut_Project_Template.zip", "Project File", "15 MB", "https://example.com/assets/capcut_template.zip")
                        )
                    )
                )
            ),

            // ==========================================
            // CONTENT CREATION
            // ==========================================
            CourseClass(
                id = "cc-class-1",
                category = CategoryType.CONTENT_CREATION,
                title = "The Viral Hook Formula & Scriptwriting",
                subtitle = "Craft undeniable opening hooks and scripts that keep viewers glued",
                description = "Learn how to formulate compelling hooks, structure educational and entertaining stories, and write scripts that convert viewers into loyal followers and clients.",
                level = "Beginner to Advanced",
                durationText = "2h 05m • 4 Videos",
                instructorName = "David Sterling",
                instructorTitle = "Media Strategist & Agency Founder",
                badge = "HIGH IMPACT",
                lessons = listOf(
                    VideoLesson(
                        id = "content-1-01",
                        classId = "cc-class-1",
                        title = "1. Anatomy of an Unstoppable Hook",
                        lessonNumber = 1,
                        durationText = "19:30",
                        youtubeVideoId = "fJ9rUzIMcZQ",
                        description = "The 5 hook archetypes: Curiosity Gap, The Negative Callout, The Counter-Intuitive Truth, The Visual Pattern Interrupt, and The Secret Reveal.",
                        keyPoints = listOf(
                            "Why the first 1.5 seconds decides 80% of video reach",
                            "Creating visual + verbal simultaneous pattern interrupts",
                            "Overcoming the 'scroll reflex' with high-contrast text hooks"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("100_High_Performing_Hooks_Swipefile.pdf", "Swipefile PDF", "3.8 MB", "https://example.com/assets/hook_swipefile.pdf")
                        )
                    ),
                    VideoLesson(
                        id = "content-1-02",
                        classId = "cc-class-1",
                        title = "2. 3-Part Micro-Storytelling Framework",
                        lessonNumber = 2,
                        durationText = "22:15",
                        youtubeVideoId = "RgKAFK5djSk",
                        description = "How to condense a powerful hero journey into 45 seconds: The Problem Hook, The Struggle/Insight Climax, and The Actionable Payoff.",
                        keyPoints = listOf(
                            "Pacing emotional stakes in under 1 minute",
                            "Avoiding boring fluff introductions ('Hey guys, welcome back')",
                            "Open loops that force viewers to watch until the last second"
                        )
                    ),
                    VideoLesson(
                        id = "content-1-03",
                        classId = "cc-class-1",
                        title = "3. Algorithm Intelligence: Watch Time, Shares & Saves",
                        lessonNumber = 3,
                        durationText = "24:00",
                        youtubeVideoId = "kffacxfA7G4",
                        description = "Demystifying YouTube Shorts, TikTok, and Instagram Reels algorithms. Which metrics actually trigger external recommendation engines.",
                        keyPoints = listOf(
                            "Why saves and shares outrank likes for viral push",
                            "Average View Duration (AVD) targets: 90%+ for under 30s",
                            "The ideal posting time and frequency myth busted"
                        )
                    ),
                    VideoLesson(
                        id = "content-1-04",
                        classId = "cc-class-1",
                        title = "4. Monetization Funnels: Digital Products & Sponsorships",
                        lessonNumber = 4,
                        durationText = "27:45",
                        youtubeVideoId = "9bZkp7q19f0",
                        description = "How to turn views into real revenue. Building an email list, selling presets and courses, and pitching sponsors even with under 10k followers.",
                        keyPoints = listOf(
                            "Lead magnets: giving away high-value freebies",
                            "Writing a media kit that brands love",
                            "Negotiating flat fee + usage rights pricing"
                        ),
                        downloadableAssets = listOf(
                            DownloadableAsset("Creator_Sponsorship_Pitch_Templates.docx", "Word Templates", "180 KB", "https://example.com/assets/sponsor_pitch.docx"),
                            DownloadableAsset("Media_Kit_Canva_Template_Link.txt", "Canva Link", "5 KB", "https://example.com/assets/mediakit_link.txt")
                        )
                    )
                )
            ),

            // ==========================================
            // SUPPORT (STUDENT HELPDESK & MENTORSHIP)
            // ==========================================
            CourseClass(
                id = "support-hub",
                category = CategoryType.SUPPORT,
                title = "Student Mentorship & Help Desk",
                subtitle = "1-on-1 mentor guidance, project critiques, VIP communities & direct assistance",
                description = "Our instructors and mentors are here to ensure you succeed. Submit your work for live critique, get answers to technical roadblocks, access exclusive downloadable project packs, and join our active student group.",
                level = "All Students",
                durationText = "Always Active • Priority Support",
                instructorName = "Masterclass Mentorship Team",
                instructorTitle = "Lead Instructors & Technical Support",
                badge = "24/7 SUPPORT",
                lessons = listOf(
                    VideoLesson(
                        id = "sup-01",
                        classId = "support-hub",
                        title = "Welcome & Student Success Walkthrough",
                        lessonNumber = 1,
                        durationText = "08:15",
                        youtubeVideoId = "CevxZvSJLk8",
                        description = "How to maximize your learning, where to find project files, how to submit assignment critiques to instructors, and how to verify your certificate upon completion.",
                        keyPoints = listOf(
                            "Downloading resources and unpacking project files",
                            "Submitting tickets for Photoshop or CapCut issues",
                            "Attending weekly live office hour streams"
                        )
                    ),
                    VideoLesson(
                        id = "sup-02",
                        classId = "support-hub",
                        title = "Troubleshooting Common Software Errors",
                        lessonNumber = 2,
                        durationText = "14:20",
                        youtubeVideoId = "Zi_XLOR8gac",
                        description = "Fix Photoshop scratch disk full errors, CapCut video black screen export glitches, phone HDR color shift issues, and audio sync problems.",
                        keyPoints = listOf(
                            "Clearing Photoshop cache and temporary files",
                            "CapCut export bitrate and frame rate mismatch fixes",
                            "Phone thermal throttling management during long shoots"
                        )
                    )
                )
            )
        )
    }

    fun getClassesForCategory(category: CategoryType): List<CourseClass> {
        return allClasses.filter { it.category == category }
    }

    fun getClassById(classId: String): CourseClass? {
        return allClasses.find { it.id == classId }
    }

    fun getLessonById(classId: String, lessonId: String): VideoLesson? {
        return getClassById(classId)?.lessons?.find { it.id == lessonId }
    }
}
