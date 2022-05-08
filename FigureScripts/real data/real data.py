import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px

pio.kaleido.scope.mathjax = None

xaxis = [10, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 50000, 100000, 500000, 1000000]

fig = go.Figure()

fig.add_trace(go.Scatter(name="DesisCen", x=xaxis, mode='lines+markers'
                         , y=[80904.27, 77070.0052, 100287.23, 85969.64, 45931.5399, 5836.943, 569.05558, 3317.3286, 3239.632366, 3187.9419, 3157.711, 3118.2859, 3089.3565]
                         , line=dict(color='rgb(99,110,250)', width=2), marker=dict(size=5, symbol='circle')))
fig.add_trace(go.Scatter(name="DesisIC", x=xaxis, mode='lines+markers'
                         , y=[3769428.71, 614002.09, 276933.59, 113125.26, 50027.37, 25677.109, 8050.648, 4493.958, 3556.81, 3232.43, 3217.23, 3189.73, 3060.95]
                         , line=dict(color='rgb(239,85,59)', width=2), marker=dict(size=5, symbol='square')))
fig.add_trace(go.Scatter(name="DesisSW", x=xaxis, mode='lines+markers'
                         , y=[7087908.72, 7115832.48, 7088310.52, 7082815.01, 6926315.17, 7110177.066, 6842615.58, 6916566.4, 7062998.53, 6664863.798, 5831647.58, 5085647.68, 4604147.195]
                         , line=dict(color='rgb(255,161,90)', width=2), marker=dict(size=5, symbol='triangle-up')))
fig.add_trace(go.Scatter(name="Desis", x=xaxis, mode='lines+markers'
                         , y=[25037257.88, 24390015.56, 24248868.78, 24602546.02, 23256023.72, 23686170.7, 23284746.21, 23700784.04, 23483457.72, 24658974.78, 21502353.92, 19009147.37, 17712309.55]
                         , line=dict(color='rgb(171,99,250)', width=2), marker=dict(size=5, symbol='cross')))

# fig.update_traces(marker_color='rgb(158,202,225)', marker_line_color='rgb(8,48,107)', marker_line_width=1.5, opacity=0.6,
# marker=dict(size=10, symbol='triangle-up'))))


#legend
fig.update_layout(
    # legend=dict(
    #     yanchor="top",
    #     y=0.99,
    #     xanchor="left",
    #     x=0.01,
    #     # bordercolor="Black",
    #     # borderwidth=2,
    #     # bgcolor="white",
    #     font=dict(
    #         size=10,
    #         color="black"
    #     ),
    # ),
    yaxis=dict(
        title_text="events/sec",
        titlefont=dict(size=15),
        ticktext=["0", "2M", "5M", "10M", "20M", "25M"],
        tickvals=[0, 2000000, 5000000, 10000000, 20000000, 25000000],
        tickmode="array",
        range=[0, 30000000],
    ),
    xaxis=dict(
        title_text="queries",
        titlefont=dict(size=15),
        ticktext=["10", '100', "1000", "10000", "100000", "1000000"],
        tickvals=[10, 100, 1000, 10000, 100000, 1000000],
        range=[1,6],
        type="log"
    )
)


# size & color
fig.update_layout(
    autosize=False,
    width=600,
    height=500,
    paper_bgcolor='rgba(0,0,0,0)',
    plot_bgcolor='rgba(0,0,0,0)'
)

# fig.update_yaxes(automargin=True)
# fig.update_yaxes(ticks="outside", tickwidth=1, tickcolor='black', ticklen=5)
fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror=True)

fig.show()
if not os.path.exists("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s3"):
    os.mkdir("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s3")

# fig.write_image("images/fig1.svg")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s3\/realdata.pdf")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s3\/realdata.svg")