

conf {
    //defaults
    glue = "com.netaporter.pws.automation.nap.cucumber.steps " +
            "com.netaporter.pws.automation.shared.steps " +
            "com.netaporter.test.utils.cucumber.glue"
    //don't forget to leave a space between values when concatenating!!!!
    path = "classpath:com/netaporter/pws/nap/cucumber/features/"
}
// Environment names should be lowercase, field values concatenated
environments {
    channelizedwebapp{
        conf {
            tags = "@nap "+
                   "@Channelized " +
                   "~@slp " +
                    "~@nonJekins"
        }
    }

    nonchannelizedwebapp{
        conf {
            tags = "@nap " +
                   "@NonChannelized " +
                    "~@slp " +
                    "~@nonJekins"
        }
    }

    channelizedcatalogue{
        conf {
            tags = "@slp " +
                   "@Channelized " +
                    "~@nonJekins"
        }
    }

    nonchannelizedcatalogue{
        conf {
            tags = "@slp " +
                   "@NonChannelized " +
                    "~@nonJekins"
        }
    }

    channelizedpdpcatalogue{
        conf {
            tags = "@napeval " +
                    "@Channelized " +
                    "~@nonJekins"
        }
    }

    nonchannelizedpdpcatalogue{
        conf {
            tags = "@napeval " +
                    "@NonChannelized " +
                    "~@nonJekins"
        }
    }

    channelizedresponsive{
        conf {
            tags = "@responsive " +
                    "@Channelized " +
                    "~@nonJekins"
        }
    }

    nonchannelizedresponsive{
        conf {
            tags = "@responsive " +
                    "@NonChannelized " +
                    "~@nonJekins"
        }
    }

    channelizedgirdle{
        conf {
            tags = "@girdleSanity " +
                    "@Channelized"
        }
    }

    nonchannelizedgirdle{
        conf {
            tags = "@girdleSanity " +
                    "@NonChannelized"
        }
    }

    channelizedlivechat{
        conf {
            tags = "@chat " +
                    "@Channelized"
        }
    }

    nonchannelizedlivechat{
        conf {
            tags = "@chat " +
                    "@NonChannelized"
        }
    }

    ecommchannelized{
        conf {
            tags = "@ecomm " +
                    "~@known-failure "+
                    "@allChannels " +
                    "~@nonJekins"

        }
    }
    napecommchannelized{
        conf {
            tags = "@nap " +
                   "@ecomm " +
                   "@Channelized " +
                    "~@nonJekins"
        }
    }

    napecommnonchannelized{
        conf {
            tags = "@nap " +
                   "@ecomm " +
                   "@NonChannelized " +
                    "~@nonJekins"
        }
    }

    ecommnonchannelized{
        conf {
            tags = "@ecomm " +
                    "~@known-failure "+
                    "@channelSpecific " +
                    "~@nonJekins"
        }
    }

    napmobile{
        conf{
            path = "classpath:com/netaporter/pws/napmobile/cucumber/features/"
            glue = "com.netaporter.pws.automation.napmobile.cucumber.steps " +
                   "com.netaporter.test.utils.cucumber.glue"
            tags = "~@known-failure"
        }
    }
    livesanity{
        conf{
            tags = "@napLiveSanity"
        }
    }

    napdebugtask {
        conf {
            tags = "@debugTask"
        }
    }

    nappurchchannelized{
        conf {
            tags = "@nap " +
                   "@purch " +
                   "@Channelized " +
                   "~@nonJekins"
        }
    }

    nappurchnonchannelized{
        conf {
            tags = "@nap " +
                   "@purch " +
                   "@NonChannelized " +
                    "~@nonJekins"
        }
    }
    napfindnonchannelized{
        conf {
            tags = "@nap " +
                    "@napfind " +
                    "@NonChannelized " +
                    "~@nonJekins"
        }
    }
    napfindchannelized{
        conf {
            tags = "@nap " +
                    "@napfind " +
                    "@Channelized " +
                    "~@nonJekins"

        }
    }

}